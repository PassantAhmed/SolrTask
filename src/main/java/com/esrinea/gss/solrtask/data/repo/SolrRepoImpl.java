/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.data.repo;

import com.esrinea.gss.solrtask.entity.SolrCollection;
import com.esrinea.gss.solrtask.data.dto.SolrMessageResponse;
import com.esrinea.gss.solrtask.entity.SolrCollectionDocument;
import com.esrinea.gss.solrtask.entity.SolrCollectionDocumentField;
import com.esrinea.gss.solrtask.entity.SolrCollectionField;
import com.esrinea.gss.solrtask.entity.SolrQueryCollection;
import com.esrinea.gss.solrtask.exception.BusinessException;
import com.esrinea.gss.solrtask.util.ConstantsHelper;
import com.esrinea.gss.solrtask.util.ErrorConstantsHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

/**
 *
 * @author passant.swelum
 */
@Repository
public class SolrRepoImpl implements SolrRepo{
    
    @Autowired
    private Environment environment;
    
    @Autowired
    private SolrClient solrClient;
    
    @Autowired 
    SolrTransactionsRepo solrTransactionsRepo;
    
    @Override
    public SolrMessageResponse addCollection(SolrCollection collection) {
        List<SolrCollectionField> fields = new ArrayList<SolrCollectionField>();
        try{
            if(solrTransactionsRepo.isExistCollection(collection.getName())){
                throw new BusinessException(ErrorConstantsHelper.COLLECTION_FOUND);
            }
            CollectionAdminResponse response = null;
            CollectionAdminRequest.Create collectionAdminRequest =
                CollectionAdminRequest
                .createCollection(collection.getName(), environment.getProperty("solr.config"),
                    Integer.valueOf(environment.getProperty("solr.shards.no")), 
                    Integer.valueOf(environment.getProperty("solr.replicationfactor"))
                ).setMaxShardsPerNode(Integer.valueOf(environment.getProperty("solr.maxshardspernode")));
            response = collectionAdminRequest.process(solrClient);
            // if response success add fields to this collection
            if(response==null){
                throw new BusinessException(ErrorConstantsHelper.FAILED_TO_CREATE_COLLECTION);
            } else{
                if(!response.isSuccess()){
                    throw new BusinessException(ErrorConstantsHelper.FAILED_TO_CREATE_COLLECTION);
                } 
                if(collection.getFields()!=null){
                    for(SolrCollectionField field : collection.getFields()){
                        addCollectionField(field, collection.getName());
                        // adding inserted fields in case we needed them in rollback-mode
                        fields.add(field);
                    }
                } 
            }
        }catch(BusinessException exception){
            // calling this method to perfom rollback manually 
            reversedAddCollectionTransaction(collection.getName(), fields);
            throw new BusinessException(exception.getMessage());
        }
        catch (SolrServerException exception) {
            reversedAddCollectionTransaction(collection.getName(), fields);
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_CREATE_COLLECTION);
        } 
        catch (IOException exception) {
            reversedAddCollectionTransaction(collection.getName(), fields);
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_CREATE_COLLECTION);
        }
        return 
            new SolrMessageResponse<String>(ConstantsHelper.SUCCESS_CODE, ConstantsHelper.SUCCESS_MESSAGE, collection.getName());
    }
    
    private void reversedAddCollectionTransaction(String collectionName, List<SolrCollectionField> fields){
        for(SolrCollectionField field : fields){
            solrTransactionsRepo.deleteField(collectionName, field.getName());
        }
        if(solrTransactionsRepo.isExistCollection(collectionName)){
            solrTransactionsRepo.deleteCollection(collectionName);
        }
    }
    
    private void addCollectionField(SolrCollectionField field, String collectionName){
        try{
            if(solrTransactionsRepo.isExistField(collectionName, field.getName())){
                throw new BusinessException(ErrorConstantsHelper.FIELD_FOUND);
            } 
            else{
                Map<String, Object> fieldAttributes = new HashMap<String, Object>();
                fieldAttributes.put(ConstantsHelper.FIELD_NAME, field.getName());
                fieldAttributes.put(ConstantsHelper.FIELD_TYPE, field.getType());
                fieldAttributes.put(ConstantsHelper.FIELD_STORED_STATE, field.isStored());
                fieldAttributes.put(ConstantsHelper.FIELD_INDEXED_STATE, field.isIndexed());
                fieldAttributes.put(ConstantsHelper.FIELD_MULTIVALUE_STATE, field.isMultivalue());
                fieldAttributes.put(ConstantsHelper.FIELD_REQUIRED_STATE, field.isRequired());
                SchemaRequest.AddField schemaRequest = new SchemaRequest.AddField(fieldAttributes);
                schemaRequest.process(solrClient, collectionName);
            } 
        } catch (SolrServerException exception) { 
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_ADD_FIELDS);
        } catch (IOException exception) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_ADD_FIELDS);
        }
    }
      
    @Override
    public void deleteCollection(String collectionName){
        solrTransactionsRepo.deleteCollection(collectionName);
    }
    
    @Override
    public SolrMessageResponse addDocument(SolrCollectionDocument document) {
        try{
            if(!solrTransactionsRepo.isExistCollection(document.getCollectionName())){
                throw new BusinessException(ErrorConstantsHelper.COLLECTION_NOT_FOUND);
            }
            SolrInputDocument solrInputDocument = new SolrInputDocument();
            for(SolrCollectionDocumentField field : document.getFields()){
                solrInputDocument.addField(field.getFieldName(), field.getFieldValue());   
            }
            solrClient.add(document.getCollectionName(), solrInputDocument);
            solrClient.commit(document.getCollectionName());
        } catch (SolrServerException exception) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_ADD_DOCUMENT);
        } catch (IOException exception) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_ADD_DOCUMENT);
        }
        return new SolrMessageResponse(ConstantsHelper.SUCCESS_CODE, ConstantsHelper.SUCCESS_MESSAGE, null);
    }
    
    
    @Override
    public void deleteDocument(String collectionName, List<SolrQueryCollection> queries){
        try{
            if(!solrTransactionsRepo.isExistCollection(collectionName)){
                throw new BusinessException(ErrorConstantsHelper.COLLECTION_NOT_FOUND); 
            } else{
                String fullQueryConditions = getConcatenatedQueryString(queries);
                solrClient.deleteByQuery(collectionName, fullQueryConditions);
                solrClient.commit(collectionName);
            }
        } catch (SolrServerException exception) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_DELETE_RECORD);
        } catch (IOException exception) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_DELETE_RECORD);
        }
    }
    
    private String getConcatenatedQueryString(List<SolrQueryCollection> queries){
        String fullQueryConditionsString = queries.get(0).getQueryKey().concat(":").concat(queries.get(0).getQueryValue());
        for(int counter = 1; counter<queries.size();counter++){
            fullQueryConditionsString.concat("&")
                .concat(queries.get(counter).getQueryKey()).concat(":").concat(queries.get(counter).getQueryValue());
        }
        return fullQueryConditionsString;
    }

    @Override
    public SolrMessageResponse getDocuments(String collectionName, List<SolrQueryCollection> queries) {
        SolrDocumentList documents = null;
        try{
            if(!solrTransactionsRepo.isExistCollection(collectionName)){
                throw new BusinessException(ErrorConstantsHelper.COLLECTION_NOT_FOUND); 
            } else{
                String fullQueryConditions = getConcatenatedQueryString(queries);
                //Preparing Solr query 
                SolrQuery solrQuery = new SolrQuery();  
                solrQuery.setQuery(fullQueryConditions);  
                //Adding the field to be retrieved 
                solrQuery.addField("*");  
                //Executing the query 
                QueryResponse queryResponse = solrClient.query(collectionName, solrQuery);  
                //Storing the results of the query 
                documents = queryResponse.getResults();  
            }
        } catch (SolrServerException exception) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_GET_DOCUMENTS);
        } catch (IOException exception) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_GET_DOCUMENTS);
        }
        return new SolrMessageResponse(ConstantsHelper.SUCCESS_CODE, ConstantsHelper.SUCCESS_MESSAGE, documents);
    }
    
}

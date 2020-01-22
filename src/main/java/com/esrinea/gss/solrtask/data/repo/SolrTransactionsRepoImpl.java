/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.data.repo;

import com.esrinea.gss.solrtask.exception.BusinessException;
import com.esrinea.gss.solrtask.util.ConstantsHelper;
import com.esrinea.gss.solrtask.util.ErrorConstantsHelper;
import java.io.IOException;
import java.util.List;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

/**
 *
 * @author passant.swelum
 */
@Repository
public class SolrTransactionsRepoImpl implements SolrTransactionsRepo{

    @Autowired
    private Environment environment;
    
    @Autowired
    private SolrClient solrClient;
    
    @Override
    public void deleteField(String collectionName, String fieldName) {
        try {
            if(isExistField(collectionName, fieldName)){
                SchemaRequest.DeleteField field = new SchemaRequest.DeleteField(fieldName);
                field.process(solrClient, collectionName);
            } 
        }catch (SolrServerException ex) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_DELETE_FIELD);
        } catch (IOException ex) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_DELETE_FIELD);
        }
    }

    @Override
    public void deleteCollection(String collectionName) {
        try{
            if(!isExistCollection(collectionName)){
                throw new BusinessException(ErrorConstantsHelper.COLLECTION_NOT_FOUND);
            }
            CollectionAdminResponse response = null;
            CollectionAdminRequest.Delete collectionAdminDeleteRequest = CollectionAdminRequest.deleteCollection(collectionName);
            response = collectionAdminDeleteRequest.process(solrClient);
            if(!response.isSuccess()){
                throw new BusinessException(ErrorConstantsHelper.FAILED_TO_DELETE_COLLECTION);
            }
        }catch (SolrServerException exception) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_DELETE_COLLECTION);
        } 
        catch (IOException exception) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_TO_DELETE_COLLECTION);
        }
    }
    
    @Override
    public boolean isExistField(String collectionName,String fieldName){
        boolean result = false;
        try{
            SchemaRequest.Fields fields = new SchemaRequest.Fields();
            SchemaResponse.FieldsResponse response = fields.process(solrClient, collectionName);
            for(int counter=0;counter<response.getFields().size();counter++){
                String name = response.getFields().get(counter).get(ConstantsHelper.FIELD_NAME).toString();
                if(name!=null && name.equals(fieldName)){
                    result = true;
                }
            }
        } catch (SolrServerException ex) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_WHILE_FINDING_FIELD);
        } catch (IOException ex) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_WHILE_FINDING_FIELD);
        }
        return result;  
    }
    
    @Override
    public boolean isExistCollection(String collectionName){
        boolean result = false;
        try{
            CollectionAdminResponse collectionsResponse = null;
            CollectionAdminRequest.List collectionsListRequest = new CollectionAdminRequest.List();
            collectionsResponse = collectionsListRequest.process(solrClient);
            List<String> collectionNames = (List<String>) collectionsResponse.getResponse().get(ConstantsHelper.COLLECTIONS);
            for(String name : collectionNames){
                if(collectionName.equals(name)){
                    result = true;
                }
            }
        } catch (SolrServerException exception) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_WHILE_FINDING_COLLECTION);
        } catch (IOException exception) {
            throw new BusinessException(ErrorConstantsHelper.FAILED_WHILE_FINDING_COLLECTION);
        }
        return result;
    }
}

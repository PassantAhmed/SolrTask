/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.service;

import com.esrinea.gss.solrtask.entity.SolrCollection;
import com.esrinea.gss.solrtask.data.dto.SolrMessageResponse;
import com.esrinea.gss.solrtask.data.repo.SolrRepo;
import com.esrinea.gss.solrtask.entity.SolrCollectionDocument;
import com.esrinea.gss.solrtask.entity.SolrQueryCollection;
import com.esrinea.gss.solrtask.exception.BusinessException;
import com.esrinea.gss.solrtask.util.ErrorConstantsHelper;
import com.esrinea.gss.solrtask.util.ValidationHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author passant.swelum
 */
@Service
public class SolrServiceImpl implements SolrService{

    @Autowired
    private SolrRepo solrRepo;
    
    @Override
    public SolrMessageResponse saveCollection(SolrCollection collection) {
        if(ValidationHelper.checkString(collection.getName())){
            return solrRepo.addCollection(collection);
        } else{
            throw new BusinessException(ErrorConstantsHelper.INVALID_COLLECTION_NAME);
        }
    }

    @Override
    public void deleteCollection(String collectionName) {
        if(ValidationHelper.checkString(collectionName)){
            solrRepo.deleteCollection(collectionName);
        } else{
            throw new BusinessException(ErrorConstantsHelper.INVALID_COLLECTION_NAME);
        }
    }

    @Override
    public SolrMessageResponse addNewDocument(SolrCollectionDocument document) {
        if(ValidationHelper.checkString(document.getCollectionName())){
            return solrRepo.addDocument(document);
        } else{
            throw new BusinessException(ErrorConstantsHelper.INVALID_COLLECTION_NAME);
        } 
    }

    @Override
    public void deleteDocument(String collectionName, List<SolrQueryCollection> queries) {
        boolean result = true;
        if(!ValidationHelper.checkString(collectionName)){
            throw new BusinessException(ErrorConstantsHelper.INVALID_COLLECTION_NAME);
        }
        for(SolrQueryCollection query : queries){
            if(!(ValidationHelper.isValidQuery(query.getQueryKey()) 
                    && ValidationHelper.isValidQuery(query.getQueryValue()) )){
                result = false;  
            } 
        }
        if(!result){
            throw new BusinessException(ErrorConstantsHelper.INVALID_QUERY);
        }
        solrRepo.deleteDocument(collectionName, queries);
    }

    @Override
    public SolrMessageResponse getDocuments(String collectionName, List<SolrQueryCollection> queries) {
        boolean result = true;
        
        if(!ValidationHelper.checkString(collectionName)){
            throw new BusinessException(ErrorConstantsHelper.INVALID_COLLECTION_NAME);
        }
        
        for(int counter=0;counter<queries.size();counter++){
            if(!(ValidationHelper.isValidQuery(queries.get(counter).getQueryKey()) 
                    && ValidationHelper.isValidQuery(queries.get(counter).getQueryValue()) )){
                result = false;   
            } 
        } 
        if(!result){
            throw new BusinessException(ErrorConstantsHelper.INVALID_QUERY);
        }
        return solrRepo.getDocuments(collectionName, queries);
    }
    
}

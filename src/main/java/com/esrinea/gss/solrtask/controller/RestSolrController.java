/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.controller;

import com.esrinea.gss.solrtask.entity.SolrCollection;
import com.esrinea.gss.solrtask.data.dto.SolrMessageResponse;
import com.esrinea.gss.solrtask.entity.SolrCollectionDocument;
import com.esrinea.gss.solrtask.entity.SolrQueryCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.esrinea.gss.solrtask.service.SolrService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author passant.swelum
 */
@RestController
@RequestMapping("/api")
public class RestSolrController {
    
    @Autowired
    private SolrService solrCollectionService;
    
    // Create collection 
    @PostMapping("/collection")
    public SolrMessageResponse createCollection(@RequestBody SolrCollection collection){
        return solrCollectionService.saveCollection(collection);
    }

    // Searching (Query an existing collection documents) 
    @GetMapping("/collection/{collectionName}")
    public SolrMessageResponse getCollectionDocuments(@PathVariable String collectionName, @RequestBody List<SolrQueryCollection> queries){
        return solrCollectionService.getDocuments(collectionName, queries);
    } 
    
    // Delete collection
    @DeleteMapping("/collection/{collectionName}")
    public void deleteCollection(@PathVariable String collectionName){
        solrCollectionService.deleteCollection(collectionName);
    }  
    
    // Add record/Document to collection
    @PostMapping("/doc")
    public SolrMessageResponse createDocument(@RequestBody SolrCollectionDocument document){
        return solrCollectionService.addNewDocument(document);
    }
    
    // Delete record/Document from collection
    @DeleteMapping("/doc/{collectionName}")
    public void deleteDocumentCollection(@PathVariable String collectionName, @RequestBody List<SolrQueryCollection> queries){
        solrCollectionService.deleteDocument(collectionName, queries);
    } 
    
}

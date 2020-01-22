/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.entity;

import java.util.List;

/**
 *
 * @author passant.swelum
 */
public class SolrCollectionDocument {
    
    private String collectionName;
    private List<SolrCollectionDocumentField> fields;

    public SolrCollectionDocument() {
    }

    public SolrCollectionDocument(String collectionName, List<SolrCollectionDocumentField> fields) {
        this.collectionName = collectionName;
        this.fields = fields;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setFields(List<SolrCollectionDocumentField> fields) {
        this.fields = fields;
    }

    public List<SolrCollectionDocumentField> getFields() {
        return fields;
    }
    
}

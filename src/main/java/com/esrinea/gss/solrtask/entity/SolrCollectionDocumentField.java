/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.entity;

/**
 *
 * @author passant.swelum
 */
public class SolrCollectionDocumentField{
    
    private String fieldName;
    private String fieldValue;

    public SolrCollectionDocumentField() {
    }

    public SolrCollectionDocumentField(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldValue() {
        return fieldValue;
    }
    
}

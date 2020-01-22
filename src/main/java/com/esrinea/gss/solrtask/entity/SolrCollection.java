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
public class SolrCollection {
    
    private String name;

    List<SolrCollectionField> fields;
    
    public SolrCollection() {}

    public SolrCollection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SolrCollectionField> getFields() {
        return fields;
    }

    public void setFields(List<SolrCollectionField> fields) {
        this.fields = fields;
    }
    
}

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
public class SolrCollectionField {
    
    private String name;
    private String type;
    private boolean stored;
    private boolean indexed;
    private boolean multivalue;
    private boolean required;

    public SolrCollectionField() {
    }

    public SolrCollectionField(String name, String type, boolean stored, boolean indexed, boolean multivalue, boolean required) {
        this.name = name;
        this.type = type;
        this.stored = stored;
        this.indexed = indexed;
        this.multivalue = multivalue;
        this.required = required;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setStored(boolean stored) {
        this.stored = stored;
    }

    public boolean isStored() {
        return stored;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public void setMultivalue(boolean multivalue) {
        this.multivalue = multivalue;
    }

    public boolean isMultivalue() {
        return multivalue;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }
    
}

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
public class SolrQueryCollection{
    
    private String queryKey;
    private String queryValue;

    public SolrQueryCollection() {
    }

    public SolrQueryCollection(String queryKey, String queryValue) {
        this.queryKey = queryKey;
        this.queryValue = queryValue;
    }

    public void setQueryKey(String queryKey) {
        this.queryKey = queryKey;
    }

    public String getQueryKey() {
        return queryKey;
    }

    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }

    public String getQueryValue() {
        return queryValue;
    }
    
}

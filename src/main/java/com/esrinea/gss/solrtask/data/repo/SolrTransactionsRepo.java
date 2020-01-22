/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.data.repo;

/**
 *
 * @author passant.swelum
 */
public interface SolrTransactionsRepo {
    void deleteField(String collectionName, String fieldName);
    void deleteCollection(String collectionName);
    boolean isExistField(String collectionName,String fieldName);
    boolean isExistCollection(String collectionName);
}

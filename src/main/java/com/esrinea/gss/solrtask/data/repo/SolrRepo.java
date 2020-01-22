/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.data.repo;

import com.esrinea.gss.solrtask.entity.SolrCollection;
import com.esrinea.gss.solrtask.data.dto.SolrMessageResponse;
import com.esrinea.gss.solrtask.entity.SolrCollectionDocument;
import com.esrinea.gss.solrtask.entity.SolrQueryCollection;
import java.util.List;

/**
 *
 * @author passant.swelum
 */
public interface SolrRepo {
    SolrMessageResponse addCollection(SolrCollection collection);
    void deleteCollection(String collectionName);
    SolrMessageResponse addDocument(SolrCollectionDocument document);
    void deleteDocument(String collectionName, List<SolrQueryCollection> queries);
    SolrMessageResponse getDocuments(String collectionName, List<SolrQueryCollection> queries);
}

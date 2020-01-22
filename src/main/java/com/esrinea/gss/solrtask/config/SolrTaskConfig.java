/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author passant.swelum
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.esrinea.gss.solrtask")
@PropertySources({
    @PropertySource({"classpath:config.properties"}),
    @PropertySource({"classpath:exceptions-messages.properties"})
})
public class SolrTaskConfig {
    
    @Autowired
    private Environment environment;
    
    /**
     * 
     * @return reference from the abstract class SolrClient which refers to either HttpSolrClient or CloudSolrClient
     */
    @Bean 
    public SolrClient solrClient(){
        CloudSolrClient.Builder builder = new CloudSolrClient.Builder();
        SolrClient solrClient = builder.withSolrUrl(environment.getProperty("solr.url")).build();
        return solrClient;
    }
    /*
    @Bean 
    public SolrClient solrClient(){
        SolrClient solrClient = new ConcurrentUpdateSolrClient.Builder(environment.getProperty("solr.url")).build();
        return solrClient;
    }
    */
    
    /*
    @Bean 
    public SolrClient solrClient(){
        SolrClient solrClient = new HttpSolrClient.Builder(environment.getProperty("solr.url")).build();
        return solrClient;
    }
    */
    
}

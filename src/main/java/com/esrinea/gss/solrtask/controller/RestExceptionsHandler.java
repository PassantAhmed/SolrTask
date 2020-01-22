/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.controller;

import com.esrinea.gss.solrtask.data.dto.SolrMessageResponse;
import com.esrinea.gss.solrtask.exception.BusinessException;
import com.esrinea.gss.solrtask.util.ConstantsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author passant.swelum
 */
@ControllerAdvice
public class RestExceptionsHandler {
    
    @Autowired
    private Environment environment;
   
    @ExceptionHandler
    public ResponseEntity<SolrMessageResponse> handleBusinessException(BusinessException exception){
        SolrMessageResponse<String> response = 
            new SolrMessageResponse<String>(ConstantsHelper.CLIENT_ERROR_CODE, environment.getProperty(exception.getMessage()), null);
        return new ResponseEntity<SolrMessageResponse>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public ResponseEntity<SolrMessageResponse> handleException(Exception exception){
        exception.printStackTrace();
        SolrMessageResponse<String> response = 
            new SolrMessageResponse<String>(ConstantsHelper.SERVER_ERROR_CODE, environment.getProperty(exception.getMessage()), null);
        if(response.getMessage()==null){
            response.setMessage(ConstantsHelper.FAILURE_MESSAGE);
        }
        return new ResponseEntity<SolrMessageResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


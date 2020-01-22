/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.util;

/**
 *
 * @author passant.swelum
 */
public class ConstantsHelper {
    
    private ConstantsHelper(){}
    
    public final static String SUCCESS_MESSAGE = "Success";
    public final static String FAILURE_MESSAGE = "Failed";
    
    public final static int SUCCESS_CODE = 200;
    public final static int CLIENT_ERROR_CODE = 400;
    public final static int SERVER_ERROR_CODE = 500;
    
    public final static String FIELD_NAME = "name";
    public final static String FIELD_TYPE = "type";
    public final static String FIELD_STORED_STATE = "stored";
    public final static String FIELD_INDEXED_STATE = "indexed";
    public final static String FIELD_MULTIVALUE_STATE = "multiValued";
    public final static String FIELD_REQUIRED_STATE = "required";
    public final static String COLLECTIONS = "collections";
}

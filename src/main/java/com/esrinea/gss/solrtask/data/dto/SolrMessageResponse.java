/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.data.dto;

/**
 *
 * @author passant.swelum
 */
public class SolrMessageResponse<T extends Object> {
    private int code;
    private String message;
    private T data;

    public SolrMessageResponse() {
    }

    public SolrMessageResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

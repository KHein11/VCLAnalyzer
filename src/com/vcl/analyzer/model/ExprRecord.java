/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.model;

/**
 *
 * @author kyihein
 */
public class ExprRecord extends CmdRecord {
    private String expr;
    private String value;

    /**
     * @return the expr
     */
    public String getExpr() {
        return expr;
    }

    /**
     * @param expr the expr to set
     */
    public void setExpr(String expr) {
        this.expr = expr;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.model;

/**
 *
 * @author kyihein
 */
public class VarRecord {
    private String varName;
    private String varValue;

    public VarRecord(String varName, String varValue) {
        this.varName = varName;
        this.varValue = varValue;
    }

    /**
     * @return the varName
     */
    public String getVarName() {
        return varName;
    }

    /**
     * @param varName the varName to set
     */
    public void setVarName(String varName) {
        this.varName = varName;
    }

    /**
     * @return the varValue
     */
    public String getVarValue() {
        return varValue;
    }

    /**
     * @param varValue the varValue to set
     */
    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }
    
}

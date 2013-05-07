/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.model;

import java.util.List;

/**
 *
 * @author kyihein
 */
public class SetRecord extends CmdRecord {
    private Long varId;
    private String varName;
    private String varValue;
    private boolean multiValue;
    private List<String> valueList;
    private String valueListString;
    
    /**
     * @return the varId
     */
    public Long getVarId() {
        return varId;
    }

    /**
     * @param varId the varId to set
     */
    public void setVarId(Long varId) {
        this.varId = varId;
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

    /**
     * @return the multiValue
     */
    public boolean isMultiValue() {
        return multiValue;
    }

    /**
     * @param multiValue the multiValue to set
     */
    public void setMultiValue(boolean multiValue) {
        this.multiValue = multiValue;
    }

    /**
     * @return the valueList
     */
    public List<String> getValueList() {
        return valueList;
    }

    /**
     * @param valueList the valueList to set
     */
    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    /**
     * @return the valueListString
     */
    public String getValueListString() {
        return valueListString;
    }

    /**
     * @param valueListString the valueListString to set
     */
    public void setValueListString(String valueListString) {
        this.valueListString = valueListString;
    }
    
}

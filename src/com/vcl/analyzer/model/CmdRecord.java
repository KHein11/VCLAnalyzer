/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.model;

/**
 *
 * @author kyihein
 */
public class CmdRecord {
    private long cmdId;
    private long cmdFileId;
    private long cmdTypeId;
    private String cmdType;
    private long lineNo;
    private String cmdValue;
    private String cmdFileName;
    private int visit;
    
    /**
     * @return the cmdId
     */
    public long getCmdId() {
        return cmdId;
    }

    /**
     * @param cmdId the cmdId to set
     */
    public void setCmdId(long cmdId) {
        this.cmdId = cmdId;
    }

    /**
     * @return the cmdFileId
     */
    public long getCmdFileId() {
        return cmdFileId;
    }

    /**
     * @param cmdFileId the cmdFileId to set
     */
    public void setCmdFileId(long cmdFileId) {
        this.cmdFileId = cmdFileId;
    }

    /**
     * @return the cmdTypeId
     */
    public long getCmdTypeId() {
        return cmdTypeId;
    }

    /**
     * @param cmdTypeId the cmdTypeId to set
     */
    public void setCmdTypeId(long cmdTypeId) {
        this.cmdTypeId = cmdTypeId;
    }

    /**
     * @return the lineNo
     */
    public long getLineNo() {
        return lineNo;
    }

    /**
     * @param lineNo the lineNo to set
     */
    public void setLineNo(long lineNo) {
        this.lineNo = lineNo;
    }

    /**
     * @return the cmdType
     */
    public String getCmdType() {
        return cmdType;
    }

    /**
     * @param cmdType the cmdType to set
     */
    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    /**
     * @return the cmdValue
     */
    public String getCmdValue() {
        return cmdValue;
    }

    /**
     * @param cmdValue the cmdValue to set
     */
    public void setCmdValue(String cmdValue) {
        this.cmdValue = cmdValue;
    }

    /**
     * @return the cmdFileName
     */
    public String getCmdFileName() {
        return cmdFileName;
    }

    /**
     * @param cmdFileName the cmdFileName to set
     */
    public void setCmdFileName(String cmdFileName) {
        this.cmdFileName = cmdFileName;
    }

    /**
     * @return the visit
     */
    public int getVisit() {
        return visit;
    }

    /**
     * @param visit the visit to set
     */
    public void setVisit(int visit) {
        this.visit = visit;
    }
}

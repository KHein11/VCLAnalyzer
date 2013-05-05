/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.model;

/**
 *
 * @author kyihein
 */
public class CmdFileRecord {
    private Long fileId;
    private String fileName;
    private int visit;

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    /**
     * @return the fileId
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
    
}

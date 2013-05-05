/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.model;

/**
 *
 * @author kyihein
 */
public class AdaptRecord extends CmdRecord {
    private long adapterFileId;
    private long adaptedFileId;
    private String adapterFileName;
    private String adaptedFileName;
    private int adapterVisit;
    private int adapteeVisit;
    
    /**
     * @return the adapterFileId
     */
    public long getAdapterFileId() {
        return adapterFileId;
    }

    /**
     * @param adapterFileId the adapterFileId to set
     */
    public void setAdapterFileId(long adapterFileId) {
        this.adapterFileId = adapterFileId;
    }

    /**
     * @return the adaptedFileId
     */
    public long getAdaptedFileId() {
        return adaptedFileId;
    }

    /**
     * @param adaptedFileId the adaptedFileId to set
     */
    public void setAdaptedFileId(long adaptedFileId) {
        this.adaptedFileId = adaptedFileId;
    }

    /**
     * @return the adapterFileName
     */
    public String getAdapterFileName() {
        return adapterFileName;
    }

    /**
     * @param adapterFileName the adapterFileName to set
     */
    public void setAdapterFileName(String adapterFileName) {
        this.adapterFileName = adapterFileName;
    }

    /**
     * @return the adaptedFileName
     */
    public String getAdaptedFileName() {
        return adaptedFileName;
    }

    /**
     * @param adaptedFileName the adaptedFileName to set
     */
    public void setAdaptedFileName(String adaptedFileName) {
        this.adaptedFileName = adaptedFileName;
    }

    /**
     * @return the adapterVisit
     */
    public int getAdapterVisit() {
        return adapterVisit;
    }

    /**
     * @param adapterVisit the adapterVisit to set
     */
    public void setAdapterVisit(int adapterVisit) {
        this.adapterVisit = adapterVisit;
    }

    /**
     * @return the adapteeVisit
     */
    public int getAdapteeVisit() {
        return adapteeVisit;
    }

    /**
     * @param adapteeVisit the adapteeVisit to set
     */
    public void setAdapteeVisit(int adapteeVisit) {
        this.adapteeVisit = adapteeVisit;
    }
    
}

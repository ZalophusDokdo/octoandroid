package com.nairbspace.octoandroid.data.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "PRINTER".
 */
public class Printer {

    private Long id;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String apiKey;
    /** Not-null value. */
    private String scheme;
    /** Not-null value. */
    private String host;
    private int port;
    private String versionJson;
    private String connectionJson;
    private String printerStateJson;

    public Printer() {
    }

    public Printer(Long id) {
        this.id = id;
    }

    public Printer(Long id, String name, String apiKey, String scheme, String host, int port, String versionJson, String connectionJson, String printerStateJson) {
        this.id = id;
        this.name = name;
        this.apiKey = apiKey;
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.versionJson = versionJson;
        this.connectionJson = connectionJson;
        this.printerStateJson = printerStateJson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getApiKey() {
        return apiKey;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /** Not-null value. */
    public String getScheme() {
        return scheme;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    /** Not-null value. */
    public String getHost() {
        return host;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getVersionJson() {
        return versionJson;
    }

    public void setVersionJson(String versionJson) {
        this.versionJson = versionJson;
    }

    public String getConnectionJson() {
        return connectionJson;
    }

    public void setConnectionJson(String connectionJson) {
        this.connectionJson = connectionJson;
    }

    public String getPrinterStateJson() {
        return printerStateJson;
    }

    public void setPrinterStateJson(String printerStateJson) {
        this.printerStateJson = printerStateJson;
    }

}

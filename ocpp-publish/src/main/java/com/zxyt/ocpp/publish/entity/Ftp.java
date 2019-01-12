package com.zxyt.ocpp.publish.entity;

public class Ftp {

    private String host;
    private int port;
    private String path;
    private String user;
    private String password;
    private String localPath;
    private String uploadPath;

    public Ftp() {
    }

    public Ftp(String host, int port, String path, String user, String password, String localPath, String uploadPath) {
        this.host = host;
        this.port = port;
        this.path = path;
        this.user = user;
        this.password = password;
        this.localPath = localPath;
        this.uploadPath = uploadPath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
}

package com.ducnv1106.message.notification;

public class Data {

    private String title;
    private String icon;
    private String body;
    private String user;
    private String sented;

    public Data(String title, String icon, String body, String user, String sented) {
        this.title = title;
        this.icon = icon;
        this.body = body;
        this.user = user;
        this.sented = sented;
    }

    public Data() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSented() {
        return sented;
    }

    public void setSented(String sented) {
        this.sented = sented;
    }
}

package com.arvind.mycomplain;

/**
 * Created by arvind on 29/3/16.
 */
public class Navitems {
    private boolean showNotify;
    private String title;


    public Navitems() {

    }

    public Navitems(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

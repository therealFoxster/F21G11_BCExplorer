package com.example.bcexplorer.info;

import java.io.Serializable;

public class CreditsItemDetail implements Serializable {
    private String title;
    private String content;

    public CreditsItemDetail(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

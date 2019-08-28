package com.jerry.control.bean;

/**
 * @author Jerry
 * @createDate 2019-05-25
 * @copyright www.aniu.tv
 * @description
 */
public class SelectBean {
    private String title;
    private String content;
    private boolean selected;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

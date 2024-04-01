package com.example.demo.pojo;

import lombok.ToString;


@ToString
public class Feedback {
    private int id;
    private String content;
    private String contact;
    private String handle;
    private boolean isHandle;
    private boolean isDelete;

    public Feedback() {
    }

    public Feedback(int id, String content, String contact, String handle, boolean isHandle, boolean isDelete) {
        this.id = id;
        this.content = content;
        this.contact = contact;
        this.handle = handle;
        this.isHandle = isHandle;
        this.isDelete = isDelete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getHandle() {
        return handle;
    }

    public boolean getisDelete() {
        return isDelete;
    }

    public void setisDelete(boolean delete) {
        isDelete = delete;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public boolean getisHandle() {
        return isHandle;
    }

    public void setisHandle(boolean isHandle) {
        this.isHandle = isHandle;
    }

}

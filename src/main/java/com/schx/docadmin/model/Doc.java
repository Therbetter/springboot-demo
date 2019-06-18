package com.schx.docadmin.model;

import java.sql.Blob;

/**
 * @description:
 * @author: xutao
 * @create: 2019-06-18 11:17
 **/
public class Doc {

    private int id;

    private byte[] file;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}

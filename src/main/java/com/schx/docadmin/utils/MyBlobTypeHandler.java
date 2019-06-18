package com.schx.docadmin.utils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyBlobTypeHandler extends BaseTypeHandler<byte[]> {
    public MyBlobTypeHandler() {
    }

    public void setNonNullParameter(PreparedStatement ps, int i, byte[] parameter, JdbcType jdbcType) throws SQLException {
        ByteArrayInputStream bis = new ByteArrayInputStream(parameter);
        ps.setBinaryStream(i, bis, parameter.length);
    }

    public byte[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getBytes(columnName);
//        Blob blob = rs.getBlob(columnName); jdbc驱动没有实现方法 报错 fuck
//        byte[] returnValue = null;
//        if (null != blob) {
//            returnValue = blob.getBytes(1L, (int)blob.length());
//        }
//
//        return returnValue;
    }

    public byte[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        Blob blob = rs.getBlob(columnIndex);
//        byte[] returnValue = null;
//        if (null != blob) {
//            returnValue = blob.getBytes(1L, (int)blob.length());
//        }
//
//        return returnValue;
        return rs.getBytes(columnIndex);
    }

    public byte[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Blob blob = cs.getBlob(columnIndex);
        byte[] returnValue = null;
        if (null != blob) {
            returnValue = blob.getBytes(1L, (int)blob.length());
        }

        return returnValue;
    }
}

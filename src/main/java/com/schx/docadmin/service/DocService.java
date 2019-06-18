package com.schx.docadmin.service;

import com.schx.docadmin.dao.DocsDao;
import com.schx.docadmin.dao.StudentDao;
import com.schx.docadmin.model.Doc;
import com.schx.docadmin.model.Student;
import com.schx.docadmin.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * @description:
 * @author: xutao
 * @create: 2019-06-13 10:30
 **/
@Service
public class DocService {

    @Autowired
    private DocsDao docsDao;

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public Doc testInsert(){
        Doc doc=new Doc();
        try {
            doc.setFile(FileUtils.file2byte("C:\\Users\\51196\\Documents\\项目文档\\招标系统\\招标采购管理系统方案.docx"));
            this.docsDao.insert(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public Doc findByid(int id){
        return this.docsDao.findByid(id);
    }
}

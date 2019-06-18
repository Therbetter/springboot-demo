package com.schx.docadmin.service;

import com.schx.docadmin.dao.StudentDao;
import com.schx.docadmin.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: xutao
 * @create: 2019-06-13 10:30
 **/
@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public Student testInsert(){
        Student s=new Student();
        s.setName("5555555555");
        s.setAge(10);
        s.setSex("1");
        s.setDepartment("啊啊");
        this.studentDao.insert(s);
        return s;
    }

}

package com.schx.docadmin.action;

import com.schx.docadmin.aop.annotation.Login;
import com.schx.docadmin.model.Doc;
import com.schx.docadmin.model.Student;
import com.schx.docadmin.service.DocService;
import com.schx.docadmin.service.RedisClient;
import com.schx.docadmin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @description:
 * @author: xutao
 * @create: 2019-06-13 10:36
 **/
@Controller
@RequestMapping("/test")
public class TestAction {

    @Autowired
    private StudentService studentService;

    @Autowired
    private DocService docService;

    @Autowired
    private RedisClient redisClient;

    @ResponseBody
    @RequestMapping("/testinsert")
    public Student testinsert(){
        return  this.studentService.testInsert();
    }

    @ResponseBody
    @Login
    @RequestMapping("/testlogin")
    public String testlogin(){
        return  "testlogin";
    }

    @RequestMapping("/myjet")
    public String testjet(ModelMap modelMap){
        modelMap.addAttribute("name","徐涛");
        return "testjet";
    }

    @RequestMapping("/testinsertfile")
    @ResponseBody
    public int testjet(){
        return docService.testInsert().getId();
    }

    @RequestMapping(value = "/testgetfile",method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource>  testfindFile(){
       Doc doc= this.docService.findByid(1);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=test.doc");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(doc.getFile().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new ByteArrayInputStream(doc.getFile())));
    }
    @RequestMapping("/testredis")
    @ResponseBody
    public String testredis(){
        redisClient.set("testkey","123456");
        return redisClient.get("testkey");
    }
}

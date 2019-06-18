package com.schx.docadmin.action;

import com.schx.docadmin.aop.annotation.Login;
import com.schx.docadmin.model.Student;
import com.schx.docadmin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

}

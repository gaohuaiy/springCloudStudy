package com.course.business.controller;

import com.course.server.domain.Test;
import com.course.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class testController {
    @Autowired
    private TestService testService;
    @RequestMapping("/test")
    public List<Test> tets(){
        List<Test> list = testService.list();
        return list;
    }
}

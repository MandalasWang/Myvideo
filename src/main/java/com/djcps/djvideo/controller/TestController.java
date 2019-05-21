package com.djcps.djvideo.controller;

import com.djcps.djvideo.common.RetResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/api/v1")
public class TestController {

    @RequestMapping("/add")
    public void test(){
        RetResponse.makeOKRsp();
    }
}

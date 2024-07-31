package com.powernode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class OrderController {



    @GetMapping("doOrder")
    public String doOrder(){
        try {//客户端那边不能超过2秒超时，先注释。默认是1秒
            // 模拟操作数据库等 耗时2s
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "油条豆浆-热干面";
    }
}

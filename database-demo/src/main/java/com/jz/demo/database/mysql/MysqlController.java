package com.jz.demo.database.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MysqlController {

    @Autowired
    private MysqlService mysqlService;

    @GetMapping("/api/m/test")
    public void test() {
        mysqlService.test();
    }
}

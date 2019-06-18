package com.schx.docadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DocadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocadminApplication.class, args);
    }

}

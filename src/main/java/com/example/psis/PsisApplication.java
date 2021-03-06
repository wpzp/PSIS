package com.example.psis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import xyz.erupt.core.annotation.EruptScan;

@SpringBootApplication
@EntityScan
@EruptScan
public class PsisApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsisApplication.class, args);
        System.out.println();
        System.out.println();
        System.err.println("库销存管理系统启动成功...");
    }

}

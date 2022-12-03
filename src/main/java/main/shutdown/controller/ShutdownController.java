package main.shutdown.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/shutdown")
public class ShutdownController {

    @Autowired private ApplicationContext context;

    @PutMapping("")
    public void close() {
        SpringApplication.exit(context);
    }
}
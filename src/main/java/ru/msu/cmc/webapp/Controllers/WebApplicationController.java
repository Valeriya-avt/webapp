package ru.msu.cmc.webapp.Controllers;

import org.springframework.boot.SpringApplication;

public class WebApplicationController {
    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(WebApplicationController.class, args);
    }
}

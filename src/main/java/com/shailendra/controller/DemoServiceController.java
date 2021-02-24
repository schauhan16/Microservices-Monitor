package com.shailendra.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author Shailendra Chauhan
 * <p>
 * Demo service which is used in monitoring
 */
@RestController
@RequestMapping("/demo")
public class DemoServiceController {

    private Random random = new Random();

    /**
     * Sample response. Should throw an exception 1 out of 5 times
     *
     * @return string
     */
    @GetMapping("/status")
    public String status() {

        if (random.nextInt(5) % 5 == 0) {
            throw new RuntimeException("Intended exception");
        }

        return "Up and Running";
    }
}

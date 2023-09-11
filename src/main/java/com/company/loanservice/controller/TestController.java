package com.company.loanservice.controller;

import com.company.loanservice.dto.TestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {
    private final Environment environment;

    @Value("${server.port}")
    String serverPort;

    @GetMapping("/get")
    public TestResponse getValue(HttpServletRequest request, HttpServletResponse response){

        log.info(String.format("Loan Server port :: %s", serverPort));
        return new TestResponse(environment.getProperty("server.port", Integer.class));
    }

}

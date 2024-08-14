package com.example.jpashop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class MvcTestController {

    @PostMapping("/test")
    public void test(@RequestBody Map<String, Object> paramMap, BindingResult bindingResult) {
        log.info("test");
        log.info("paramMap: {}", paramMap);
        log.info("paramMap: {}", paramMap.get("id"));
        log.info("paramMap: {}", paramMap.get("pw"));
        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());
    }

}

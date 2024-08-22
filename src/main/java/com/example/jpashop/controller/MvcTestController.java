package com.example.jpashop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@Slf4j
public class MvcTestController {

    @PostMapping("/login")
    public void login(@RequestBody Map<String, Object> paramMap , BindingResult bindingResult ,HttpServletRequest request) {
        log.info("login");
        log.info("paramMap: {}", paramMap.get("id"));
        log.info("paramMap: {}", paramMap.get("pw"));

        if (bindingResult.hasErrors()) {
            System.out.println("에러발생");
        }


        HttpSession session = request.getSession();
        session.setAttribute("id", paramMap.get("id"));
        session.setMaxInactiveInterval(1800); //1800초
    }

    @PostMapping("/select")
    public void select(HttpServletRequest request) {
        log.info("select");


        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("세션 없으면 가라");
            return;
        }
        log.info("id:{}",session.getAttribute("id"));

        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}",
                        name, session.getAttribute(name)));
        log.info("sessionId={}", session.getId());
        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new
                Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());

    }


    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        log.info("logout");


        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("세션 없으면 가라");
            return;
        }else{
            System.out.println("세션 삭제");
            session.invalidate();//세션 삭제
        }
        //에러발생
        //log.info("id:{}",session.getAttribute("id"));
    }

}

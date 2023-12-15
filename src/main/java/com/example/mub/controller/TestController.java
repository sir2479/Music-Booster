package com.example.mub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TestController {


	@GetMapping("/blank-page")
    public String blank() {
		log.info("blank 페이지 출력");

        return "blank-page";
    }
	
	@GetMapping("/index-test")
    public String home2() {
		log.info("인덱스2 페이지 출력");

        return "index-test";
    }
	
	@GetMapping("/test")
    public String test() {
		log.info("인덱스2 페이지 출력");

        return "test";
    }
	
	@GetMapping("/tables-basic")
    public String table() {
		log.info("인덱스2 페이지 출력");

        return "tables-basic";
    }

}

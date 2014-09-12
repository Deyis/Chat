package com.hifive.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

//  http://localhost:8000/HelloChat/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "index";
    }


//  http://localhost:8000/HelloChat/hello.json
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public HelloResponse sayHello() {
        return new HelloResponse("Hello World!!!");
    }

    private class HelloResponse {
        private String greeting;
        public HelloResponse(String greeting) {
            this.greeting = greeting;
        }

        public String getGreeting() {
            return greeting;
        }

        public void setGreeting(String greeting) {
            this.greeting = greeting;
        }
    }
}




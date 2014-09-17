package com.hifive.chat.controller;

import com.hifive.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

//  http://localhost:8000/HelloChat/
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "index";
    }


//  http://localhost:8000/HelloChat/hello.json
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public HelloResponse sayHello() {
        return new HelloResponse("Hello " + userRepository.getUserById(new Long(1)).getUserName() + "!!!");
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




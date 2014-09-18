package com.hifive.chat.controller;


import com.hifive.security.model.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

//  http://localhost:8000/HelloChat/
//    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "index";
    }


//  http://localhost:8000/HelloChat/hello.json
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public HelloResponse sayHello() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        TODO add password encryption
        return new HelloResponse("Hello, " + user.getUsername() + "!!!, Your password is: " + user.getPassword());
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




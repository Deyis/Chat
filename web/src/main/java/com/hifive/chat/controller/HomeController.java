package com.hifive.chat.controller;


import com.hifive.chat.web.request.RegisterRequest;
import com.hifive.chat.web.response.RegistrationResponse;
import com.hifive.chat.web.response.UserDetailsResponse;
import com.hifive.security.model.User;
import com.hifive.security.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

//  http://localhost:8000/HelloChat/
//    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "/static/index.html";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public RegistrationResponse register(@RequestBody RegisterRequest request) {
        System.out.println("Register request");
        User user = userRepository.registerUser(request.getLogin(), request.getPassword(), "ROLE_USER");
        System.out.println("Created user id: " + user.getId());
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
        System.out.println("Create response");
        return new RegistrationResponse();
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


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    @ResponseBody
    public UserDetailsResponse getDetails() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserDetailsResponse(user.getUsername());
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




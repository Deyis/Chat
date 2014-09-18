package com.hifive.security.handler;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


@Component("failureHandler")
public class FailureHandler extends SimpleUrlAuthenticationFailureHandler {
}

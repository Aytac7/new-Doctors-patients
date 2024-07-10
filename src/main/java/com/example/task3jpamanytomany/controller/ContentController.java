package com.example.task3jpamanytomany.controller;

import com.example.task3jpamanytomany.security.MyUserDetailService;
import com.example.task3jpamanytomany.webtoken.JwtService;
import com.example.task3jpamanytomany.webtoken.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ContentController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final MyUserDetailService myUserDetailService;

  @GetMapping("/home")
  public String handleWelcome() {
    return "home";
  }

  @GetMapping("/admin/home")
  public String handleAdminHome() {
    return "home_admin";
  }

  @GetMapping("/user/home")
  public String handleUserHome() {
    return "home_user";
  }

//  @GetMapping("/login")
//  public String handleLogin() {
//    return "custom_login";
//  }


  //islemir
//  @PostMapping("/authenticate")
//  public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
//    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//            loginForm.username(), loginForm.password()
//    ));
//    if (authentication.isAuthenticated()) {
//      return jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
//    } else {
//      throw new UsernameNotFoundException("Invalid credentials");
//    }
//  }


  @PostMapping("/authenticate")
  @ResponseBody
  public Map<String, String> authenticateAndGetToken(@RequestBody LoginForm loginForm) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginForm.username(), loginForm.password()
    ));
    if (authentication.isAuthenticated()) {
      String jwtToken = jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
      Map<String, String> response = new HashMap<>();
      response.put("token", jwtToken);
      return response;
    } else {
      throw new UsernameNotFoundException("Invalid credentials");
    }
  }

}
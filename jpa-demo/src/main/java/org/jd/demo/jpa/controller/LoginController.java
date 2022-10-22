package org.jd.demo.jpa.controller;

import org.jd.demo.jpa.pojo.request.LoginUser;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@Slf4j
@RestController
public class LoginController {

  @PostMapping(value = "/login")
  public Object login(@RequestBody LoginUser loginUser,
      HttpServletRequest request, HttpServletResponse response) {
    try {
      request.login(loginUser.getEmail(), loginUser.getPassword());
    } catch (ServletException e) {
      e.printStackTrace();
      return "fail";
    }
    response.addHeader(HttpHeaders.AUTHORIZATION, "1234");
    return "success";
  }

  @PostMapping(value = "/api/logout")
  public Object logout(HttpServletRequest request, HttpServletResponse response) {
    try {
      request.logout();
    } catch (ServletException e) {
      return "fail";
    }
    return "success";
  }

}

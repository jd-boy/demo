package org.jd.demo.jpa.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jd.demo.jpa.utils.JwtUtils;
import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Auther jd
 */
@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  @SneakyThrows
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    Map<String, String> map = objectMapper
        .readValue(request.getInputStream(), new TypeReference<Map<String, String>>() {});
    return super.getAuthenticationManager()
        .authenticate(new UsernamePasswordAuthenticationToken(map.get("email"), map.get("password"), null));
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    DefaultUserDetail userDetail = (DefaultUserDetail) authResult.getPrincipal();
    response.setContentType("application/json;charset=UTF-8");
    objectMapper.writeValue(response.getOutputStream(),
        JwtUtils.createToken(userDetail.getEmail()));
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException failed) throws IOException, ServletException {
    response.setContentType("application/json;charset=UTF-8");
    objectMapper.writeValue(response.getOutputStream(), "login fail");
  }

  @Autowired
  @Override
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }
}

package org.jd.demo.jpa.config;

import org.jd.demo.jpa.utils.JwtUtils;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @Auther jd
 */
@Slf4j
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (Objects.nonNull(token)) {
      String username = null;
      if (!JwtUtils.isExpired(token)) {
        username = JwtUtils.getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (Objects.nonNull(userDetails)) {
          SecurityContextHolder.getContext()
              .setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, token,
                  userDetails.getAuthorities()));
        }
      }
    }
    filterChain.doFilter(request, response);
  }

}
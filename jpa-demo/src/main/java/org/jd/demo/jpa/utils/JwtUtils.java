package org.jd.demo.jpa.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

/**
 * @Auther jd
 */
public class JwtUtils {

  private static final String secretKey = "jzffffffff";

  public static String createToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + 10 * 1000))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compressWith(CompressionCodecs.GZIP)
        .compact();
  }

  public static String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody().getSubject();
  }

  public static boolean isExpired(String token) {
    Claims claims = null;
    try {
      claims = Jwts.parser().setSigningKey(secretKey)
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      claims = e.getClaims();
    }
    return new Date(System.currentTimeMillis()).after(claims.getExpiration());
  }

}

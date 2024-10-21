package com.example.liveboard.global.jwt;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

  private static final String AUTHORIZATION_KEY = "Bearer";

  private final String REFRESH_TOKEN = "refresh";

  private final String ACCESS_TOKEN = "access";

  private final long ACCESS_TOKEN_TIME = 24 * 60 * 60 * 1000L; // 1일

  private final long REFRESH_TOKEN_TIME = 30 * 24 * 60 * 60 * 1000L; // 1달

  @Value("${jwt.secret}")
  private String secretKey;

  private Key key;

  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  public String createAccessToken(String username) {
    Date date = new Date();

    return Jwts.builder()
        .setSubject(username)
        .claim(AUTHORIZATION_KEY, "USER")
        .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
        .setIssuedAt(date)
        .signWith(key, signatureAlgorithm)
        .compact();
  }

  public void addJwtToCookie(String tokenName, String JWT, HttpServletResponse res) {

    Cookie cookie = new Cookie(tokenName, JWT);
    cookie.setPath("/");
    cookie.setMaxAge(7 * 60 * 60 * 24); // 7일

    if(tokenName.equals("refreshToken")) {
      cookie.setHttpOnly(true);
    }

    res.addCookie(cookie);
  }

  public boolean validateToken(String token) throws ExpiredJwtException {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

      return true;
    } catch (SecurityException | MalformedJwtException | SignatureException e) {
      logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      logger.error("Expired JWT token, 만료된 JWT token 입니다.");
      throw e;
    } catch (UnsupportedJwtException e) {
      logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
    }

    return false;
  }

  public Claims getUserInfoFromToken(String token) {

    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  // 추후 accessToken 검증 추가
  public String getTokenFromRequest(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        try {

          return URLDecoder.decode(cookie.getValue(), "UTF-8");
        } catch (UnsupportedEncodingException e) {

          return null;
        }
      }
    }

    return null;
  }
}
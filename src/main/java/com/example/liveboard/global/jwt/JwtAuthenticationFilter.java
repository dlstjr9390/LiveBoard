package com.example.liveboard.global.jwt;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 인가")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String tokenValue = jwtUtil.getTokenFromRequest(request);

    if(StringUtils.hasText(tokenValue)) {
      if(jwtUtil.validateToken(tokenValue)) {
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        String username = info.getSubject();
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication  = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
      } else {
        throw new IllegalArgumentException("유효 하지 않은 토큰");
      }
    }

    filterChain.doFilter(request,response);
  }
}
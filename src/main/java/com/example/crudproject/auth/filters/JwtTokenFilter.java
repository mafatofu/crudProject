package com.example.crudproject.auth.filters;

import com.example.crudproject.auth.jwt.JwtTokenUtils;
import com.example.crudproject.auth.service.JpaUserDetailManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//한번만 체크
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    //jwt 토큰 발급을 위한 클래스
    private final JwtTokenUtils jwtTokenUtils;
    // 사용자 정보를 찾기위한 UserDetailsService 또는 Manager
    //private final UserDetailsManager manager;
    private final JpaUserDetailManager manager;

    public JwtTokenFilter(
            JwtTokenUtils jwtTokenUtils,
            JpaUserDetailManager manager

    ) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.manager = manager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        log.debug("try jwt filter");
        //1.리퀘스트의 헤더에서 "Authorization"을 받는다.
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 2. Authorization 헤더가 존재하는지 + Bearer로 시작하는지 체크
        if (authHeader!=null && authHeader.startsWith("Bearer ")){
            String token = authHeader.split(" ")[1];
            //3. 유효한 토큰인지 체크
            //정상적이지 않으면 validate메서드에서 exception 발생
            if (jwtTokenUtils.validate(token)){
                // 4. 유효하다면 해당 토큰을 바탕으로 사용자 정보를 SecurityContext에 등록
                SecurityContext context
                        = SecurityContextHolder.createEmptyContext();
                //사용자 정보 회수
                //username인지 email인지 체크해볼것
                String email = jwtTokenUtils
                        .parseClaims(token)
                        .getSubject();
                UserDetails userDetails = manager.loadUserByEmail(email);
                //권한 추가
                for (GrantedAuthority authority :userDetails.getAuthorities()) {
                    log.info("authority: {}", authority.getAuthority());
                }

                // 인증 정보 생성
                AbstractAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                token,
                                userDetails.getAuthorities()
                        //TODO 현재는 빈 권한
                );
                // 인증 정보 등록
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
                log.info("set security context with jwt");

            } else {
                log.warn("jwt validation failed");
            }
        }
        // 5. 다음 필터 호출
        // doFilter를 호출하지 않으면 Controller까지 요청이 도달하지 못한다.
        filterChain.doFilter(request, response);
    }
}

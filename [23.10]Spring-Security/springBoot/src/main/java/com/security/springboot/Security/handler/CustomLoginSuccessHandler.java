package com.security.springboot.Security.handler;

import com.security.springboot.domain.User.Model.UserDetailsVO;
import com.security.springboot.domain.User.Model.UserEntity;
import com.security.springboot.domain.User.Model.UserVO;
import com.security.springboot.domain.User.Role.UserRole;
import com.security.springboot.jwt.AuthConstants;
import com.security.springboot.jwt.JWTProvider;
import com.security.springboot.utils.ConvertUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

// 인증(로그인) 성공한 이후 추가 처리 로직.
@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("3.CustomLoginSuccessHandler");

        JSONObject jsonObject; // response로 내보려는 정보를 담은 Json 객체
        HashMap<String, Object> responseMap = new HashMap<>(); // response 할 데이터를 담기 위한 맵
        UserDetailsVO userDetailsVO = (UserDetailsVO) authentication.getPrincipal(); // userDetailsVO 조회
        UserEntity userEntity = userDetailsVO.getUserEntity(); // 사용자와 관련된 정보 조회
        JSONObject userEntityJson = (JSONObject) ConvertUtil.convertObjectToJsonObject(userEntity); // 사용자 정보 Json 객체로 변환
        String accessToken = JWTProvider.generateJwtToken(userDetailsVO); // accessToken 생성
        String refreshToken = JWTProvider.generateRefreshToken(userDetailsVO); // refreshToken 생성
        ResponseCookie responseCookie = JWTProvider.generateRefreshTokenCookie(refreshToken); // refreshCookie 만들기

        if (userEntity.getRole() == UserRole.ROLE_ADMIN) {
            responseMap.put("userInfo", userEntityJson); // 유저 정보 Json 형식으로 넣기
            responseMap.put("msg", "관리자 로그인 성공");
        } else {
            responseMap.put("userInfo", userEntityJson); // 유저 정보 Json 형식으로 넣기
            responseMap.put("msg", "일반 사용자 로그인 성공");
        }

        jsonObject = new JSONObject(responseMap);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + accessToken); // header Access Token 추가
        response.addHeader(AuthConstants.COOKIE_HEADER, responseCookie.toString()); // refresh token cookie 추가
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);
        printWriter.flush();
        printWriter.close();

    }

}

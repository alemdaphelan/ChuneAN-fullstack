package vn.com.chunean.chunean.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.services.AuthService;
import vn.com.chunean.chunean.services.JwtService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@RestController
@RequestMapping("/auth/google")
@RequiredArgsConstructor
public class GoogleController {
    private final AuthService authService;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    private final String redirectUrl = "http://localhost:8080/auth/google/callback";

    private final JwtService jwtService;

    @GetMapping
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        String authUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8) +
                "&response_type=code" +
                "&scope=openid profile email" +
                "&access_type=offline" +
                "&prompt=consent";

        response.sendRedirect(authUrl);
    }

    @GetMapping("/callback")
    public void callback(HttpServletResponse response, @RequestParam("code") String code) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = "https://oauth2.googleapis.com/token";
        MultiValueMap<String,String> tokenRequest = new LinkedMultiValueMap<>();

        //Get access token from Google
        tokenRequest.add("client_id", clientId);
        tokenRequest.add("client_secret", clientSecret);
        tokenRequest.add("code", code);
        tokenRequest.add("redirect_uri", redirectUrl);
        tokenRequest.add("grant_type", "authorization_code");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(tokenRequest,headers);
        ResponseEntity<String> tokenResponse = restTemplate.postForEntity(tokenUrl, request, String.class);

        if(!tokenResponse.getStatusCode().is2xxSuccessful()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String accessToken = new ObjectMapper().readTree(tokenResponse.getBody()).get("access_token").asText();

        //Get user's info by sending access token
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<String> userInfoResponse = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.GET,
                userInfoRequest,
                String.class);
        if(!userInfoResponse.getStatusCode().is2xxSuccessful()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        JsonNode userInfo = new ObjectMapper().readTree(userInfoResponse.getBody());
        String email = userInfo.get("email").asText();
        String username = userInfo.get("name").asText();
        String avatarUrl = userInfo.get("picture").asText();
        User optUser = authService.getUserByEmailOrUsername(username,email);
        if(optUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(username);
            newUser.setAvatarUrl(avatarUrl);
            optUser = authService.createUser(newUser);
        }

        //Set JWT to header for response
        String jwt = jwtService.generateJwt(optUser.getId());
        ResponseCookie cookie = ResponseCookie.from("jwt",jwt)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofHours(24))
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE,cookie.toString());
        response.sendRedirect("http://localhost:5173/home");
    }
}

package vn.com.chunean.chunean.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.chunean.chunean.services.JwtService;
import vn.com.chunean.chunean.services.UserService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/auth/google")
@RequiredArgsConstructor
public class GoogleController {
    @Value("${GOOGLE_CLIENT_ID}")
    private String clientId;
    @Value("${GOOGLE_CLIENT_SECRET}")
    private String clientSecret;

    private final String redirectUrl = "http://localhost:8080/auth/google/callback";

    private final UserService userService;
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

    }
}

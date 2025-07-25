package vn.com.chunean.chunean.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.services.JwtService;
import vn.com.chunean.chunean.services.UserService;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public JwtFilter(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws  ServletException, IOException
    {
        String token = null;
        Cookie[] cookie = request.getCookies();

        if(cookie != null){
            for(Cookie c : cookie){
                if(c.getName().equals("jwt")){
                    token = c.getValue();
                }
            }
        }

        if(token != null && jwtService.validateJwt(token)){
            try{
                String id =  jwtService.extractUserId(token);
                User user = userService.getUserById(id);
                if(user != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,token, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }catch(Exception e){
                logger.warn(e.getMessage());
            }
        }
        filterChain.doFilter(request,response);
    }
}

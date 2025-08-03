package vn.com.chunean.chunean.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.services.AuthService;
import vn.com.chunean.chunean.services.JwtService;
import vn.com.chunean.chunean.services.UserService;
import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final AuthService authService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
    throws  ServletException, IOException
    {
        String path = request.getRequestURI();
        if(path.startsWith("/auth")){
            filterChain.doFilter(request,response);
            return;
        }
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
                User user = authService.getUserById(id);
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

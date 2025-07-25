package vn.com.chunean.chunean.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class JwtService {
    public String generateJwt(String userId){
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
    }
    public boolean validateJwt(String jwt) {
        try{
            Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    public String extractUserId(String token) {
        return Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody().getSubject();
    }
}

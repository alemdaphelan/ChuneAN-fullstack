package vn.com.chunean.chunean.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
@Service
public class JwtService {
    final String secret = "oiCaiDitMeCuocDoiToiYeuCuocDoiNayRatLaNhieuHaHaCaiDitConMeCayVaiLonDuMe";
    final SecretKey  secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    public String generateJwt(String userId){
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean validateJwt(String jwt) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    public String extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

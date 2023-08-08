package uz.securty.securty.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {

    private String secret;
    private int jwtExpirationInMs;
    private int jwtExpirationInMsRememberMe;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("${jwt.jwtExpirationInMs}")
    public void setJwtExpirationInMs(String jwtExpirationInMs) {
        this.jwtExpirationInMs = Integer.parseInt(jwtExpirationInMs);
    }

    @Value("${jwt.jwtExpirationInMsRememberMe}")
    public void setJwtExpirationInMsRememberMe(String jwtExpirationInMsRememberME) {
        this.jwtExpirationInMsRememberMe = Integer.parseInt(jwtExpirationInMsRememberME);
    }


    // generate token for user
    public String generateToken(UserDetails userDetails, boolean rememberMe) {
        Map<String, Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();



        claims.put("roles", roles.stream().map(e -> {
            return e.getAuthority().toString();
        }).toArray());


        return doGenerateToken(claims, userDetails.getUsername(), rememberMe);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, boolean rememberMe) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (rememberMe ? jwtExpirationInMsRememberMe : jwtExpirationInMs))).signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    public boolean validationToken(String autToken){
        try {
            Jws<Claims> claimsJ = Jwts.parser().setSigningKey(secret).parseClaimsJws(autToken);
            return  true;
        }
        catch (SignatureException | MalformedJwtException | UnsupportedJwtException  | IllegalArgumentException ex){
            System.out.println("Notogri");
            throw new BadCredentialsException("INVALID_CREDENTIALS" ,ex);

        } catch (ExpiredJwtException ex){

            System.out.println("Muddat eskirgan");
            return  false;

        }

    }

    public String getUsernameFromToken(String token){
        Claims claims =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


    public List<SimpleGrantedAuthority>  getRoleFromToken(String authToken){
        List<SimpleGrantedAuthority> roles = null;
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();

        roles = ((List<String>) claims.get("roles" ,List.class))
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return roles;

    }







}

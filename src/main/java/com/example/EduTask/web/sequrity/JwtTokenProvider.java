package com.example.EduTask.web.sequrity;


import com.example.EduTask.domain.exceptions.AccessDeniedException;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.service.UserService;
import com.example.EduTask.service.props.JwtProperties;
import com.example.EduTask.web.dto.auth.JwtRefresh;
import com.example.EduTask.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;



import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(
            final Long userId,
            final String email
    ) {
        Claims claims = Jwts.claims().subject(email).add("id", userId).build();

        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }


    public String createRefreshToken(
            final Long userId,
            final String email) {

        Claims claims = Jwts.claims().subject(email).add("id", userId).build();

        Instant validity = Instant.now().plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }


    public JwtResponse refreshUserTokens(final JwtRefresh refreshToken) {

        JwtResponse jwtResponse = new JwtResponse();

        String token = refreshToken.getRefreshToken();

        if (!isValid(token)) {
            throw new AccessDeniedException();
        }

        Long userId = getId(token);
        User user = userService.getById(userId);

        jwtResponse.setId(userId);
        jwtResponse.setEmail(user.getEmail());
        jwtResponse.setAccessToken(createAccessToken(userId,user.getEmail()));
        jwtResponse.setRefreshToken(createRefreshToken(userId,user.getEmail()));

        return jwtResponse;
    }


    public boolean isValid(final String token) {
        Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

        return claims.getPayload().getExpiration().after(new Date());
    }

    private Long getId(final String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);
    }

    private String getEmail(
            final String token
    ) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Authentication getAuthentication(final String token) {
        String email = getEmail(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}

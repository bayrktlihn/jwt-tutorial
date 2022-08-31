package io.bayrktlihn;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Random;

public class AppExample {
    public static void main(String[] args) {

        Instant now = Instant.now();

        byte[] secret = decodeBase64("U3hKCOTBH2UidSktilD+iIlkmDICQnx0O6f/owb9BZ5EReRA").getBytes();


        SecretKey secretKey = Keys.hmacShaKeyFor(secret);

        String jwt = Jwts.builder()
                .setSubject("Alihan Bayraktar")
                .setAudience("video demo")
                .claim("1d20", new Random().nextInt(20) + 1)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.MINUTES)))
                .signWith(secretKey)
                .compact();

        System.out.println(jwt);

        Jws<Claims> claimsJws = Jwts.parser()
                .requireAudience("video demo")
                .setAllowedClockSkewSeconds(61)
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt);

        System.out.println(claimsJws);

        System.out.println("1d20: " + claimsJws.getBody().get("1d20", Integer.class));


    }


    public static String decodeBase64(String encodedBase64) {
        byte[] decoded = Base64.getDecoder().decode(encodedBase64);
        return new String(decoded);
    }

    public static String encodeBase64(String decodedBase64) {
        byte[] encoded = Base64.getEncoder().encode(decodedBase64.getBytes());
        return new String(encoded);
    }
}

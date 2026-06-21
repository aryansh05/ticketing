package io.github.aryansh05.ticketing.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final String issuer;
    private final long accessTtlSeconds;

    public JwtUtil(
            @Value("${security.jwt.private-key}") String jwtPrivateKey,
            @Value("${security.jwt.public-key}") String jwtPublicKey,
            @Value("${security.jwt.issuer}") String issuer,
            @Value("${security.jwt.access-ttl-seconds}") long accessTtlSeconds
    ) throws Exception {
        this.privateKey = loadPrivateKey(jwtPrivateKey);
        this.publicKey = loadPublicKey(jwtPublicKey);
        this.issuer = issuer;
        this.accessTtlSeconds = accessTtlSeconds;
    }

    private PrivateKey loadPrivateKey(String key) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("Ed25519")
                .generatePrivate(spec);
    }

    private PublicKey loadPublicKey(String key) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return KeyFactory.getInstance("Ed25519")
                .generatePublic(spec);
    }

    public String generateAccessToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .issuer(issuer)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(accessTtlSeconds)))
                .claim("type", "access")
                .signWith(privateKey, Jwts.SIG.EdDSA)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        return "access".equals(extractClaims(token).get("type"));
    }

    public String getUserId(String token) {
        return extractClaims(token).getSubject();
    }
}

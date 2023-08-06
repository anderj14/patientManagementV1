package PatientAPI.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "cV/RfXhfxYXL9lsXiJMmKxpy/6Ee" +
            "ihTiEzaEs6IYycRe0g0T4ta/0vVKe/sEJXi" +
            "Wg9K01jAonvxqNnpBRz1Jk0RW9ffQIwL3ixeUhyKlU5" +
            "wd7IlZvOAEy9PcC8yYdYz1W6jk3HugPKXAdDM/y1mSpJvSvg" +
            "Tc/JwUXlMMEv0nq9weRsrw/5wmRiIAnh4jRJ/PO6kTM46QSKZffik" +
            "UBrb5SakVfzj59UQ2LRj56LYmYRzof2moKUn4JOopGMaSi3ZHAWm4gBa0+P" +
            "Cxx3tlErEkS2dcZ/YjOFeO2UdE1Gv+4uvrXV44/3psZuRUUyxBZhLTNe1F9n7CVpK" +
            "qh6oScu/khl7dDM+D7JLfThTZd4Hqzkw=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    // Method generator for implement a token
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Validate the token
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    //Validate token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Extract the expiration date
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode((SECRET_KEY));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

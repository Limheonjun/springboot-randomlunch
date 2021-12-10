package com.emgc.payhere.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public final class JwtUtil {

	private static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60; //1시간

	private static final String SECRET_KEY = "payhere";

	private JwtUtil() {}

	public static String createToken(String email, String role) {
		Claims claims = Jwts.claims().setSubject(email);
		claims.put("role", role);
		Date now = new Date();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.compact();
	}

	public static String getUserEmail(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}

	public static String getUserRole(String token) {
		return (String)Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("role");
	}

	public static String resolveToken(HttpServletRequest request) {
		return request.getHeader("Token");
	}

	public static boolean isValidated(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

}

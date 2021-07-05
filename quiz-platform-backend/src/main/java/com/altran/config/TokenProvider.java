package com.altran.config;

import static com.altran.util.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.altran.util.Constants.AUTHORITIES_KEY;
import static com.altran.util.Constants.SIGNING_KEY;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Represents provider's token
 * 
 * @author Maha.BSaid
 * @version 1.0
 */
@Component
public class TokenProvider implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Gets userName from token
	 * 
	 * @param token the token of authenticated user
	 * @return userName of authenticated user
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * Gets expiration date from token
	 * 
	 * @param token the token of authenticated user
	 * @return expiration date of token
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * Represents Generic method to get one claim
	 * 
	 * @param token          the token of authenticated user
	 * @param claimsResolver
	 * @return claim
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Gets all details of token
	 * 
	 * @param token the token of authenticated user
	 * @return all claims
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
	}

	/**
	 * Test if token have been expired
	 * 
	 * @param token the token of authenticated user
	 * @return status of token
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * Generates token of authenticated user
	 * 
	 * @param authentication represents the signature for an authentication request
	 * @return generated token
	 */
	public String generateToken(Authentication authentication) {
		final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.signWith(SignatureAlgorithm.HS256, SIGNING_KEY).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000)).compact();
	}

	/**
	 * Verifies the validity of token
	 * 
	 * @param token       the token of authenticated user
	 * @param userDetails provides core user informations
	 * @return validity of token
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	/**
	 * Gets authentication informations: userDetails and authorities
	 * 
	 * @param token        the token of authenticated user
	 * @param existingAuth
	 * @param userDetails
	 * @return UserName and Password Authentication Token
	 */
	UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth,
			final UserDetails userDetails) {

		final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);

		final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

		final Claims claims = claimsJws.getBody();

		final Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}

}

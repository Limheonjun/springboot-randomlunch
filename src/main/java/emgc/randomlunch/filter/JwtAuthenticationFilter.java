package emgc.randomlunch.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.emgc.payhere.util.JwtUtil;

import emgc.randomlunch.service.function.UserService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final UserService userService;

	public JwtAuthenticationFilter(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String token = JwtUtil.resolveToken(request);

		if (token != null && !userService.isTokenExpired(token) && JwtUtil.isValidated(token)) {

			String email = JwtUtil.getUserEmail(token);
			String role = JwtUtil.getUserRole(token);
			Authentication authentication = new UsernamePasswordAuthenticationToken(email, "",
				Arrays.asList(new SimpleGrantedAuthority(role)));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

}

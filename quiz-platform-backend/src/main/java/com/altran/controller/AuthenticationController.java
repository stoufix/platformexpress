package com.altran.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altran.config.TokenProvider;
import com.altran.model.LoginUser;
import com.altran.model.User;
import com.altran.repository.UserRepository;
import com.altran.util.Translator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.support.MessageSourceAccessor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static com.altran.util.Constants.ERROR;
import static com.altran.util.Constants.MESSAGE;
import static com.altran.util.Constants.INACTIVE_ACCOUNT;
import static com.altran.util.Constants.WRONG_PASS;
import static com.altran.util.Constants.WRONG_LOGIN;

/**
 * Represents Rest controller of Authentication
 * 
 * @author Maha.BSaid
 * @version 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	protected final Log logger = LogFactory.getLog(getClass());

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	/**
	 * Verifies if user exists, active and verifies if userName and password are
	 * correct
	 * 
	 * @param loginUser
	 * @return ResponseEntity: the object or the error to display when
	 *         authenticating
	 */
	@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> register(@RequestBody LoginUser loginUser) {
		Map<String, Object> response = new HashMap<>();
		response.put(ERROR, false);
		try {
			User user = userRepository.findByUsername(loginUser.getUsername());
			boolean password = bcryptEncoder.matches(loginUser.getPassword(), user.getPassword());
			if (password) {
				if (user.isActivated()) {

					final Authentication authentication = authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					final String token = jwtTokenUtil.generateToken(authentication);
					response.put(ERROR, false);
					response.put(MESSAGE, token);
				} else {
					response.put(ERROR, true);
					response.put(MESSAGE, Translator.toLocale(INACTIVE_ACCOUNT));
				}
			} else {

				response.put(ERROR, true);
				response.put(MESSAGE, Translator.toLocale(WRONG_PASS));
			}
		} catch (NullPointerException e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(WRONG_LOGIN));
		}
		return ResponseEntity.ok(response);

	}

}

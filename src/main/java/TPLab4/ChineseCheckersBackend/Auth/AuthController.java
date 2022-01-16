package TPLab4.ChineseCheckersBackend.Auth;

import javax.validation.Valid;

import TPLab4.ChineseCheckersBackend.Role.ERole;
import TPLab4.ChineseCheckersBackend.Role.Role;
import TPLab4.ChineseCheckersBackend.Role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TPLab4.ChineseCheckersBackend.Request.LoginRequest;
import TPLab4.ChineseCheckersBackend.Request.SignupRequest;
import TPLab4.ChineseCheckersBackend.Response.JwtResponse;
import TPLab4.ChineseCheckersBackend.Response.MessageResponse;
import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserDetailsImpl;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller for authentication
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
	/**
	 * Authentication menager
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * User repository
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Role repository
	 */
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Password encoder
	 */
	@Autowired
	private PasswordEncoder encoder;

	/**
	 * Jwt utilities
	 */
	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * Method for handling sign in request.
	 * @param loginRequest Login request
	 * @return Server response
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
	{
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
				userDetails.getId(), 
				userDetails.getUsername(),
				roles));
	}

	/**
	 * Method for handling sign up request.
	 * @param signUpRequest Register request
	 * @return Server response
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
	{
		if(userRepository.existsByUsername(signUpRequest.getUsername())) 
		{
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Username is already taken!"));
		}

		User user = new User(signUpRequest.getUsername(), 
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if(strRoles == null)
		{
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		}
		else {
			strRoles.forEach(role -> {
				switch(role)
				{
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
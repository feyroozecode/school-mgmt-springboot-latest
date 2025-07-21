package ne.ecole.schoolmgmt.service;

import ne.ecole.schoolmgmt.dto.*;
import ne.ecole.schoolmgmt.entity.User;
import ne.ecole.schoolmgmt.exception.BadRequestException;
import ne.ecole.schoolmgmt.exception.ResourceNotFoundException;
import ne.ecole.schoolmgmt.repository.UserRepository;
import ne.ecole.schoolmgmt.security.JwtUtil;
import ne.ecole.schoolmgmt.security.UserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    public AuthResponse login(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetailsServiceImpl.UserPrincipal userPrincipal = 
                (UserDetailsServiceImpl.UserPrincipal) authentication.getPrincipal();
            
            User user = userPrincipal.getUser();
            
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());
            
            UserDto userDto = modelMapper.map(user, UserDto.class);
            
            return new AuthResponse(token, userDto);
            
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Email ou mot de passe incorrect");
        }
    }

    public AuthResponse register(CreateUserRequest request) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Un utilisateur avec cet email existe déjà");
        }

        // Créer le nouvel utilisateur
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setPhone(request.getPhone());
        user.setIsActive(true);

        user = userRepository.save(user);

        // Générer le token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());
        
        UserDto userDto = modelMapper.map(user, UserDto.class);
        
        return new AuthResponse(token, userDto);
    }

    public UserDto getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto updateProfile(String email, UpdateUserRequest request) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public void changePassword(String email, ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Mot de passe actuel incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}


package org.busnake.biblioteka_api.controller;

import org.busnake.biblioteka_api.assembler.UserAssembler;
import org.busnake.biblioteka_api.model.dto.requests.LoginRequestDTO;
import org.busnake.biblioteka_api.model.dto.requests.RegisterRequestDTO;
import org.busnake.biblioteka_api.model.dto.responses.LoginResponseDTO;
import org.busnake.biblioteka_api.model.entities.user.User;
import org.busnake.biblioteka_api.repository.UserRepository;
import org.busnake.biblioteka_api.model.entities.user.UserRole;
import org.busnake.biblioteka_api.security.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.busnake.biblioteka_api.helper.ResponseHelper.createErrorResponse;
import static org.busnake.biblioteka_api.helper.ResponseHelper.createSuccessResponse;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserAssembler userAssembler;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, UserAssembler userAssembler, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequestDTO loginRequest){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        User user = userRepository.findByEmail(loginRequest.getEmail());

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token, user.getId(), user.getName(), user.getEmail());

        return createSuccessResponse("Usuário logado com sucesso!", HttpStatus.OK, loginResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated RegisterRequestDTO registerRequestDTO) {

        if(userRepository.findByEmail(registerRequestDTO.getEmail())!=null){
            return  createErrorResponse(
                    "Um usuário com esse email já existe!",
                    HttpStatus.BAD_REQUEST);
        }

        String hashPassword = new BCryptPasswordEncoder().encode(registerRequestDTO.getPassword());

        User user = new User(
                registerRequestDTO.getName().toLowerCase(),
                registerRequestDTO.getEmail().toLowerCase(),
                hashPassword,
                UserRole.USER
        );

        userRepository.save(user);

        return createSuccessResponse(
                "Usuário registrado com sucesso!",
                HttpStatus.CREATED,
                userAssembler.toModel(user)
        );
    }
}

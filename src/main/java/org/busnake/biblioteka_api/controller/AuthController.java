package org.busnake.biblioteka_api.controller;

import org.busnake.biblioteka_api.assembler.UserAssembler;
import org.busnake.biblioteka_api.model.dto.requests.LoginRequestDTO;
import org.busnake.biblioteka_api.model.dto.requests.RegisterRequestDTO;
import org.busnake.biblioteka_api.model.entities.User;
import org.busnake.biblioteka_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.busnake.biblioteka_api.helper.ResponseHelper.createErrorResponse;
import static org.busnake.biblioteka_api.helper.ResponseHelper.createSuccessResponse;

@RestController
public class AuthController {

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    public AuthController(UserRepository userRepository, UserAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if(user.getPasswordHash().equals(loginRequest.getPassword())){
            return createSuccessResponse(
                    "Usuário logado com sucesso!",
                    HttpStatus.OK,
                    userAssembler.toModel(user)
            );
        }

        return createErrorResponse("Email ou senha inválidos!", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDTO) {

        if(userRepository.findByEmail(registerRequestDTO.getEmail())!=null){
            return  createErrorResponse(
                    "Um usuário com esse email já existe!",
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(
                registerRequestDTO.getName(),
                registerRequestDTO.getEmail(),
                registerRequestDTO.getPassword()
        );

        userRepository.save(user);

        return createSuccessResponse(
                "Usuário registrado com sucesso!",
                HttpStatus.CREATED,
                userAssembler.toModel(user)
        );
    }
}

package org.busnake.biblioteka_api.controller;

import org.busnake.biblioteka_api.assembler.UserAssembler;
import org.busnake.biblioteka_api.exception.UserNotFoundException;
import org.busnake.biblioteka_api.model.entities.user.User;
import org.busnake.biblioteka_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.busnake.biblioteka_api.helper.ResponseHelper.createErrorResponse;
import static org.busnake.biblioteka_api.helper.ResponseHelper.createSuccessResponse;

@RestController
public class UserController implements GenericController<User> {

    private final UserRepository repository;
    private final UserAssembler assembler;

    public UserController(UserRepository userRepository, UserAssembler assembler) {
        this.repository = userRepository;
        this.assembler = assembler;
    }

    @GetMapping("/users")
    @Override
    public ResponseEntity<?> all() {
        List<User> users = repository.findAll();

        return createSuccessResponse("Usuários obtidos com sucesso!", HttpStatus.OK, assembler.toListModel(users));
    }

    @GetMapping("/users/{id}")
    @Override
    public ResponseEntity<?> one(@PathVariable Long id) {
        User user = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );

        return createSuccessResponse("Usuário obtido com sucesso!", HttpStatus.OK, assembler.toModel(user));
    }

    public ResponseEntity<?> save(User newUser) {
        return createErrorResponse("Endpoint desabilitado, use /auth/register.", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/users/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        User user = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );

        repository.deleteById(id);

        return createSuccessResponse("Usuário deletado com sucesso!", HttpStatus.OK, assembler.toModel(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody User updatedUser, @PathVariable Long id) {

        repository.findById(id).map(
                (user -> {
                    user.setName(updatedUser.getName());
                    user.setPasswordHash(updatedUser.getPasswordHash());
                    return repository.save(user);
                })
        ).orElseGet(() -> {
            return repository.save(updatedUser);
        });

        return createSuccessResponse("Usuário atualizado com sucesso!", HttpStatus.OK, assembler.toModel(updatedUser));
    }
}

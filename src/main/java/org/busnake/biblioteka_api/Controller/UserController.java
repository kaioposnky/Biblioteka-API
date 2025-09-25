package org.busnake.biblioteka_api.Controller;

import org.busnake.biblioteka_api.Assembler.UserModelAssembler;
import org.busnake.biblioteka_api.Exception.UserNotFoundException;
import org.busnake.biblioteka_api.Model.Entities.User;
import org.busnake.biblioteka_api.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.busnake.biblioteka_api.Helper.ResponseHelper.createSuccessResponse;

@RestController
public class UserController implements GenericController<User> {

    private final UserRepository repository;
    private final UserModelAssembler assembler;

    public UserController(UserRepository userRepository, UserModelAssembler assembler) {
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
    public ResponseEntity<?> one(Long id) {
        User user = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );

        return createSuccessResponse("Usuário obtido com sucesso!", HttpStatus.OK, assembler.toModel(user));
    }

    @PostMapping("/users")
    @Override
    public ResponseEntity<?> save(User newUser) {
        User user = repository.save(newUser);

        return createSuccessResponse("Usuário criado com sucesso!", HttpStatus.CREATED, assembler.toModel(user));
    }

    @DeleteMapping("/users/{id}")
    @Override
    public ResponseEntity<?> delete(Long id) {
        User user = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );

        repository.deleteById(id);

        return createSuccessResponse("Usuário deletado com sucesso!", HttpStatus.OK, assembler.toModel(user));
    }

    @PutMapping("/users/{id}")
    @Override
    public ResponseEntity<?> update(@RequestBody User updatedUser, @PathVariable Long id) {

        repository.findById(id).map(
                (user -> {
                    user.setName(updatedUser.getName());
                    user.setPassword(updatedUser.getPassword());
                    return repository.save(user);
                })
        ).orElseGet(() -> {
            return repository.save(updatedUser);
        });

        return createSuccessResponse("Usuário atualizado com sucesso!", HttpStatus.OK, assembler.toModel(updatedUser));
    }
}

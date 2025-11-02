package org.busnake.biblioteka_api.controller;

import org.busnake.biblioteka_api.assembler.UserAssembler;
import org.busnake.biblioteka_api.exception.BookLoanNotFoundException;
import org.busnake.biblioteka_api.exception.UserNotFoundException;
import org.busnake.biblioteka_api.model.dto.requests.UserUpdateDTO;
import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.busnake.biblioteka_api.model.entities.user.User;
import org.busnake.biblioteka_api.repository.BookLoanRepository;
import org.busnake.biblioteka_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.busnake.biblioteka_api.helper.ResponseHelper.createErrorResponse;
import static org.busnake.biblioteka_api.helper.ResponseHelper.createSuccessResponse;

@RestController
public class UserController implements GenericController<User> {

    private final UserRepository repository;
    private final BookLoanRepository bookLoanRepository;
    private final UserAssembler assembler;

    public UserController(UserRepository userRepository, BookLoanRepository bookLoanRepository, UserAssembler assembler) {
        this.repository = userRepository;
        this.bookLoanRepository = bookLoanRepository;
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

    @PutMapping("/users")
    public ResponseEntity<?> update(@RequestBody UserUpdateDTO updatedInfo, @AuthenticationPrincipal User userLogged) {
        Long userId = userLogged.getId();

        String passwordHash;
        if (updatedInfo.password() == null || updatedInfo.password().isBlank()){
            passwordHash = new BCryptPasswordEncoder().encode(updatedInfo.password());
        } else {
            passwordHash = null;
        }

        repository.findById(userId).map(
                (userFound -> {
                    if(updatedInfo.name() != null) userFound.setName(updatedInfo.name());
                    if(passwordHash != null) userFound.setPasswordHash(passwordHash);
                    return repository.save(userFound);
                })
        ).orElseThrow(() -> new UserNotFoundException(userId));

        return createSuccessResponse("Usuário atualizado com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/books/loans/{loanId}/user")
    public ResponseEntity<?> loanUser(@PathVariable Long loanId){
        BookLoan bookLoan = bookLoanRepository.findById(loanId).orElseThrow(
                () -> new BookLoanNotFoundException(loanId)
        );

        User user = bookLoan.getUser();

        return createSuccessResponse(
                "Usuário encontrado com sucesso!",
                HttpStatus.OK,
                assembler.toModel(user)
        );
    }
}

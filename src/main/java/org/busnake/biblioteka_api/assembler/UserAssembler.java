package org.busnake.biblioteka_api.assembler;

import org.busnake.biblioteka_api.controller.UserController;
import org.busnake.biblioteka_api.model.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler extends BaseAssembler<User> {
    public UserAssembler() {
        super(UserController.class);
    }
}

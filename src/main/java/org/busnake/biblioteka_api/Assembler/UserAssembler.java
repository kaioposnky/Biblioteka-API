package org.busnake.biblioteka_api.Assembler;

import org.busnake.biblioteka_api.Controller.UserController;
import org.busnake.biblioteka_api.Model.Entities.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler extends BaseAssembler<User> {
    public UserAssembler() {
        super(UserController.class);
    }
}

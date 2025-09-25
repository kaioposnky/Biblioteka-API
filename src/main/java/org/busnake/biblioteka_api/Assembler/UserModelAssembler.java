package org.busnake.biblioteka_api.Assembler;

import org.busnake.biblioteka_api.Controller.UserController;
import org.busnake.biblioteka_api.Model.Entities.User;

public class UserModelAssembler extends GenericAssembler<User>{
    public UserModelAssembler(UserController controller) {
        super(controller);
    }
}

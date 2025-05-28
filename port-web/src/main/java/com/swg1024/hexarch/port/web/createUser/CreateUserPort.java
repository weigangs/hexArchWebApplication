package com.swg1024.hexarch.port.web.createUser;

import com.swg1024.hexarch.port.web.createUser.command.CreateUserCommand;

public interface CreateUserPort {

    int createUser(CreateUserCommand createUserCommand);
}

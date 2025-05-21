package com.swg1024.hexarch.port.in.createUser;

import com.swg1024.hexarch.port.in.createUser.command.CreateUserCommand;

public interface CreateUserPort {

    int createUser(CreateUserCommand createUserCommand);
}

package com.swg1024.hexarch.inport.createUser;

import com.swg1024.hexarch.inport.createUser.command.CreateUserCommand;

public interface CreateUserPort {

    int createUser(CreateUserCommand createUserCommand);
}

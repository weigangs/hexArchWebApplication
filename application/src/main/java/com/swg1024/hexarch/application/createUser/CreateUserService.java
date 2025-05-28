package com.swg1024.hexarch.application.createUser;


import com.swg1024.hexarch.domain.createUser.CreateUserDomain;
import com.swg1024.hexarch.port.web.createUser.CreateUserPort;
import com.swg1024.hexarch.port.web.createUser.command.CreateUserCommand;
import com.swg1024.hexarch.port.persist.createUser.CreateUserOutPort;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CreateUserService implements CreateUserPort {

    @Autowired
    private CreateUserOutPort createUserOutPort;

    @Override
    public int createUser(CreateUserCommand createUserCommand) {

        CreateUserDomain createUserDomain =
                new CreateUserDomain(createUserCommand.getUserName(), createUserCommand.getEmail(), createUserOutPort);

        return createUserDomain.createUser();

    }
}

package com.swg1024.hexarch.port.web.createUser.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserCommand {

    private  String userName;

    private  String email;
}

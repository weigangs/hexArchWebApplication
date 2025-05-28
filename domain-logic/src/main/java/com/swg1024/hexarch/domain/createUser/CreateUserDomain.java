package com.swg1024.hexarch.domain.createUser;

import com.swg1024.hexarch.port.persist.createUser.CreateUserOutPort;
import com.swg1024.hexarch.port.persist.createUser.model.PoCreateUserModel;

public class CreateUserDomain {

    private final String userName;

    private final String email;

    private final CreateUserOutPort createUserOutPort;

    public CreateUserDomain(String userName, String email, CreateUserOutPort createUserOutPort) {
        this.userName = userName;
        this.email = email;
        this.createUserOutPort = createUserOutPort;
    }

    public boolean checkExists() {

        return false;
    }

    public int createUser() {
        PoCreateUserModel userModel = new PoCreateUserModel();
        userModel.setUserName(userName);
        userModel.setEmail(email);
        return createUserOutPort.createUser(userModel);
    }
}

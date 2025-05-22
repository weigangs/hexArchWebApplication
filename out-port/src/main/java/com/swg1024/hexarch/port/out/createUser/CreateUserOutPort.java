package com.swg1024.hexarch.port.out.createUser;

import com.swg1024.hexarch.port.out.createUser.model.PoCreateUserModel;

public interface CreateUserOutPort {

    int createUser(PoCreateUserModel userModel);
}

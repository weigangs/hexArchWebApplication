package com.swg1024.hexarch.port.persist.createUser;

import com.swg1024.hexarch.port.persist.createUser.model.PoCreateUserModel;

public interface CreateUserOutPort {

    int createUser(PoCreateUserModel userModel);
}

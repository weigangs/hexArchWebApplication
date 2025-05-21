package com.swg1024.hexarch.port.out.createUser;

import com.swg1024.hexarch.port.out.model.UserModel;

public interface CreateUserOutPort {

    int createUser(UserModel userModel);
}

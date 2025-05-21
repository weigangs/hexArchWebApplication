package com.swg1024.hexarch.port.out.queryUser;

import com.swg1024.hexarch.port.out.model.UserModel;

public interface UserQueryOutPort {

    UserModel queryUser(String userName);
}

package com.swg1024.hexarch.port.out.queryUser;

import com.swg1024.hexarch.port.out.queryUser.model.PoQueryUserModel;

public interface UserQueryOutPort {

    PoQueryUserModel queryUser(String userName);
}

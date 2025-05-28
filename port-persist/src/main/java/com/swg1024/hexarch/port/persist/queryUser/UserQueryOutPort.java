package com.swg1024.hexarch.port.persist.queryUser;

import com.swg1024.hexarch.port.persist.queryUser.model.PoQueryUserModel;

public interface UserQueryOutPort {

    PoQueryUserModel queryUser(String userName);
}

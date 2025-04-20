package com.swg1024.hexarch.outport.queryUser;

import com.swg1024.hexarch.outport.model.UserModel;

public interface UserQueryOutPort {

    UserModel queryUser(String userName);
}

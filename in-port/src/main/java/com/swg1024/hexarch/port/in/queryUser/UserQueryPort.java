package com.swg1024.hexarch.port.in.queryUser;

import com.swg1024.hexarch.port.in.queryUser.model.vo.UserModelVO;

public interface UserQueryPort {

    UserModelVO queryUserByUserName(String userName);
}

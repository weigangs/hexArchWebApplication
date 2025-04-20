package com.swg1024.hexarch.inport.queryUser;

import com.swg1024.hexarch.inport.model.vo.UserModelVO;

public interface UserQueryPort {

    UserModelVO queryUserByUserName(String userName);
}

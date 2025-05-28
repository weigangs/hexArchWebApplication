package com.swg1024.hexarch.port.web.queryUser;

import com.swg1024.hexarch.port.web.queryUser.model.vo.UserModelVO;

public interface UserQueryPort {

    UserModelVO queryUserByUserName(String userName);
}

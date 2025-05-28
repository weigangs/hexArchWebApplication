package com.swg1024.hexarch.application.queryUser;

import com.swg1024.hexarch.application.queryUser.converter.UserConverter;
import com.swg1024.hexarch.port.persist.queryUser.UserQueryOutPort;
import com.swg1024.hexarch.port.persist.queryUser.model.PoQueryUserModel;
import com.swg1024.hexarch.port.web.queryUser.UserQueryPort;
import com.swg1024.hexarch.port.web.queryUser.model.vo.UserModelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserQueryService implements UserQueryPort {

    @Autowired
    private UserQueryOutPort userQueryOutPort;

    @Override
    public UserModelVO queryUserByUserName(String userName) {
        PoQueryUserModel userModel = userQueryOutPort.queryUser(userName);

        if (Objects.isNull(userModel)) {
            return null;
        }
        return UserConverter.INSTANCE.to(userModel);
    }
}

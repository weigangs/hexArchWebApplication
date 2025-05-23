package com.swg1024.hexarch.application.queryUser;

import com.swg1024.hexarch.application.queryUser.convertor.UserConvertor;
import com.swg1024.hexarch.port.in.queryUser.model.vo.UserModelVO;
import com.swg1024.hexarch.port.in.queryUser.UserQueryPort;
import com.swg1024.hexarch.port.out.createUser.CreateUserOutPort;
import com.swg1024.hexarch.port.out.queryUser.model.PoQueryUserModel;
import com.swg1024.hexarch.port.out.queryUser.UserQueryOutPort;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserQueryService implements UserQueryPort {

    @Resource
    private UserQueryOutPort userQueryOutPort;

    @Resource
    private CreateUserOutPort createUserOutPort;

    @Override
    public UserModelVO queryUserByUserName(String userName) {
        PoQueryUserModel userModel = userQueryOutPort.queryUser(userName);

        if (Objects.isNull(userModel)) {
            return null;
        }
        return UserConvertor.INSTANCE.to(userModel);
    }
}

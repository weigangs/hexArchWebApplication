package com.swg1024.hexarch.application.queryUser;

import com.swg1024.hexarch.application.queryUser.convertor.UserConvertor;
import com.swg1024.hexarch.port.in.model.vo.UserModelVO;
import com.swg1024.hexarch.port.in.queryUser.UserQueryPort;
import com.swg1024.hexarch.port.out.model.UserModel;
import com.swg1024.hexarch.port.out.queryUser.UserQueryOutPort;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserQueryService implements UserQueryPort {

    @Resource
    private UserQueryOutPort userQueryOutPort;

    @Override
    public UserModelVO queryUserByUserName(String userName) {
        UserModel userModel = userQueryOutPort.queryUser(userName);

        if (Objects.isNull(userModel)) {
            return null;
        }
        return UserConvertor.INSTANCE.to(userModel);
    }
}

package com.swg1024.hexarch.application.queryUser;

import com.swg1024.hexarch.application.queryUser.convertor.UserConvertor;
import com.swg1024.hexarch.inport.model.vo.UserModelVO;
import com.swg1024.hexarch.inport.queryUser.UserQueryPort;
import com.swg1024.hexarch.outport.model.UserModel;
import com.swg1024.hexarch.outport.queryUser.UserQueryOutPort;
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

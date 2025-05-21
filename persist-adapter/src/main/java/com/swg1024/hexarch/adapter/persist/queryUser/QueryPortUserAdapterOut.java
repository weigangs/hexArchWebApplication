package com.swg1024.hexarch.adapter.persist.queryUser;

import com.swg1024.hexarch.adapter.persist.entity.TbUser;
import com.swg1024.hexarch.port.out.model.UserModel;
import com.swg1024.hexarch.port.out.queryUser.UserQueryOutPort;
import com.swg1024.hexarch.adapter.persist.mapper.TbUserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class QueryPortUserAdapterOut implements UserQueryOutPort {

    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public UserModel queryUser(String userName) {
        TbUser user = tbUserMapper.selectUserByName(userName);
        if (Objects.isNull(user)) {
            return null;
        }
        UserModel result = new UserModel();
        result.setUserName(user.getName());
        result.setEmail(user.getEmail());
        return result;
    }
}

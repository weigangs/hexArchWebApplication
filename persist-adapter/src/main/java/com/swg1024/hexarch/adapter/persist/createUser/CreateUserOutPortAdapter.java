package com.swg1024.hexarch.adapter.persist.createUser;

import com.swg1024.hexarch.adapter.persist.entity.TbUser;
import com.swg1024.hexarch.adapter.persist.mapper.TbUserMapper;
import com.swg1024.hexarch.port.persist.createUser.CreateUserOutPort;
import com.swg1024.hexarch.port.persist.createUser.model.PoCreateUserModel;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class CreateUserOutPortAdapter implements CreateUserOutPort {

    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public int createUser(PoCreateUserModel userModel) {

        TbUser user = new TbUser();
        user.setName(userModel.getUserName());
        user.setEmail(userModel.getEmail());
        return tbUserMapper.insertUser(user);
    }
}

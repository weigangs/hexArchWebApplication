package com.swg1024.hexarch.persistadapter.createUser;

import com.swg1024.hexarch.outport.createUser.CreateUserOutPort;
import com.swg1024.hexarch.outport.model.UserModel;
import com.swg1024.hexarch.persistadapter.entity.TbUser;
import com.swg1024.hexarch.persistadapter.mapper.TbUserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class CreateUserOutPortAdapter implements CreateUserOutPort {

    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public int createUser(UserModel userModel) {

        TbUser user = new TbUser();
        user.setName(userModel.getUserName());
        user.setEmail(userModel.getEmail());
        return tbUserMapper.insertUser(user);
    }
}

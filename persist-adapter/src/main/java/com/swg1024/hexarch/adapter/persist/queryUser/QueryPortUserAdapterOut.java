package com.swg1024.hexarch.adapter.persist.queryUser;

import com.swg1024.hexarch.adapter.persist.entity.TbUser;
import com.swg1024.hexarch.adapter.persist.mapper.TbUserMapper;
import com.swg1024.hexarch.port.persist.queryUser.UserQueryOutPort;
import com.swg1024.hexarch.port.persist.queryUser.model.PoQueryUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class QueryPortUserAdapterOut implements UserQueryOutPort {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public PoQueryUserModel queryUser(String userName) {
        TbUser user = tbUserMapper.selectUserByName(userName);
        if (Objects.isNull(user)) {
            return null;
        }
        PoQueryUserModel result = new PoQueryUserModel();
        result.setUserName(user.getName());
        result.setEmail(user.getEmail());
        return result;
    }
}

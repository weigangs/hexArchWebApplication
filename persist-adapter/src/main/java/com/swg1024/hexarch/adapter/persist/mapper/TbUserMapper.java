package com.swg1024.hexarch.adapter.persist.mapper;

import com.swg1024.hexarch.adapter.persist.entity.TbUser;
import org.apache.ibatis.annotations.Param;

public interface TbUserMapper {

    TbUser selectUserByName(@Param(value = "name") String name);

    int insertUser(TbUser tbUser);
}

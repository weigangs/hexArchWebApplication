package com.swg1024.hexarch.persistadapter.mapper;

import com.swg1024.hexarch.persistadapter.entity.TbUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface TbUserMapper {

    TbUser selectUserByName(@Param(value = "name") String name);

    int insertUser(TbUser tbUser);
}

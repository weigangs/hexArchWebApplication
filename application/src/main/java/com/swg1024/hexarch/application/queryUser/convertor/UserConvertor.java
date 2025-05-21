package com.swg1024.hexarch.application.queryUser.convertor;

import com.lkyl.oceanframework.common.utils.mapperstruct.base.BaseMapperConverter;
import com.swg1024.hexarch.port.in.model.vo.UserModelVO;
import com.swg1024.hexarch.port.out.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserConvertor extends BaseMapperConverter<UserModel, UserModelVO> {
    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);
}

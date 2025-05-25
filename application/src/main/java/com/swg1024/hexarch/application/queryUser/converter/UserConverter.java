package com.swg1024.hexarch.application.queryUser.converter;

import com.lkyl.oceanframework.common.utils.mapperstruct.base.BaseMapperConverter;
import com.swg1024.hexarch.port.in.queryUser.model.vo.UserModelVO;
import com.swg1024.hexarch.port.out.queryUser.model.PoQueryUserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserConverter extends BaseMapperConverter<PoQueryUserModel, UserModelVO> {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);
}

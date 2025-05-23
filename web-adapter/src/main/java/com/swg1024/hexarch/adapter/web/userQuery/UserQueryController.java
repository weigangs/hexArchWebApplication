package com.swg1024.hexarch.adapter.web.userQuery;

import com.lkyl.oceanframework.web.response.CommonResult;
import com.swg1024.hexarch.port.in.queryUser.model.vo.UserModelVO;
import com.swg1024.hexarch.port.in.queryUser.UserQueryPort;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserQueryController {

    @Resource
    private UserQueryPort userQueryPort;

    @GetMapping("/queryUser/{userName}")
    public CommonResult<UserModelVO> createUser(@PathVariable("userName") String userName) {
        return CommonResult.ok(userQueryPort.queryUserByUserName(userName));
    }
}

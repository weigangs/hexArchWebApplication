package com.swg1024.hexarch.adapter.web.queryUser;

import com.lkyl.oceanframework.web.response.CommonResult;
import com.swg1024.hexarch.port.web.queryUser.UserQueryPort;
import com.swg1024.hexarch.port.web.queryUser.model.vo.UserModelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserQueryController {

    @Autowired
    private UserQueryPort userQueryPort;

    @GetMapping("/queryUser/{userName}")
    public CommonResult<UserModelVO> createUser(@PathVariable("userName") String userName) {
        return CommonResult.ok(userQueryPort.queryUserByUserName(userName));
    }
}

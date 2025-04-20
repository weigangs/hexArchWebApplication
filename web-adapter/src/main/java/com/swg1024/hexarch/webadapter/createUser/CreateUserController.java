package com.swg1024.hexarch.webadapter.createUser;

import com.lkyl.oceanframework.web.response.CommonResult;
import com.swg1024.hexarch.inport.createUser.CreateUserPort;
import com.swg1024.hexarch.inport.createUser.command.CreateUserCommand;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateUserController {

    @Resource
    private CreateUserPort createUserPort;

    @PostMapping("/createUser")
    public CommonResult<String> createUser(@RequestParam("userName") String userName, @RequestParam("email") String email) {
        createUserPort.createUser(new CreateUserCommand(userName, email));
        return CommonResult.ok("ok");
    }
}

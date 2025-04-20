package com.swg1024.hexarch.webadapter.health;

import com.lkyl.oceanframework.web.response.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {


    @GetMapping("/health")
    public CommonResult<String> checkHealth() {
        return CommonResult.ok("health");
    }
}

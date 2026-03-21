package com.leoga.ecom.order.controllers;

import com.leoga.ecom.order.BuildInfo;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
Test class for configuration parameters
 */
@RestController
@AllArgsConstructor
public class BuildInfoController {

//    @Value("${build.id:default}")
//    private String buildId;
//    @Value("${build.name:default}")
//    private String buildName;
//    @Value("${build.version:default}")
//    private String buildVersion;

    private BuildInfo buildInfo;

    @GetMapping("/build-info")
    @RateLimiter(name = "rateBreaker", fallbackMethod = "getBuildInfoFallback")
    public String getBuildInfo() {
        StringBuilder result = new StringBuilder("Build info. Id: ");
        return result.append(buildInfo.getId())
                .append(" Name: ").append(buildInfo.getName())
                .append(" Version: ").append(buildInfo.getVersion())
                .toString();
    }

    public String getBuildInfoFallback(Exception exception) {
        return "Hello Fallback";
    }
}

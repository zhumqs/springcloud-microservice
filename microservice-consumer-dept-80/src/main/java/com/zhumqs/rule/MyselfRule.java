package com.zhumqs.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyselfRule {
    @Bean
    public IRule myrule() {
        //返回随机的方法
//        return new RandomRule();
        //返回自定义的方法
        return new FiveRandomRule();
    }
}

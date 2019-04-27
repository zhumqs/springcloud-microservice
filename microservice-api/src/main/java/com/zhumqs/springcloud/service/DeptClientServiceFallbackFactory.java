package com.zhumqs.springcloud.service;

import com.zhumqs.springcloud.entities.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable throwable) {
        //返回一个处理的对象, 对具体每个方法出现异常后统一在这里进行处理, 不用在controller里去进行处理
        return new DeptClientService() {
            //
            @Override
            public Dept get(long id) {
                return new Dept()
                        .setDeptno(id)
                        .setDname("该ID: "+ id + "没有对应的信息...")
                        .setDb_source("no database in MySQL");
            }

            @Override
            public List<Dept> list() {
                return null;
            }

            @Override
            public boolean add(Dept dept) {
                return false;
            }
        };
    }
}

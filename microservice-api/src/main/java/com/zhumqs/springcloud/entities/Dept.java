package com.zhumqs.springcloud.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Dept implements Serializable {
    private Long deptno;
    private String dname;
    //来自哪一个数据库，微服务架构中一个微服务可以对应一个数据库，同一个信息被存储在不同的数据库中
    private String db_source;

    public Dept(String name) {
        super();
        this.dname = name;
    }
}

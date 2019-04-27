package com.zhumqs.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zhumqs.springcloud.entities.Dept;
import com.zhumqs.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController
{
	@Autowired
	private DeptService service;

	@RequestMapping(value="/dept/add",method=RequestMethod.POST)
	public boolean add(@RequestBody Dept dept)
	{
		return service.add(dept);
	}

	//添加注解当方法报异常的时候会执行相应的方法
	@HystrixCommand(fallbackMethod = "processHystrix_Get")
	@RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
	public Dept get(@PathVariable("id") Long id)
	{
		Dept dept = service.get(id);
		if(dept == null) {
			throw new RuntimeException("该Id: "+ id + "没有对应的信息...");
		}
		return service.get(id);
	}

	@RequestMapping(value="/dept/list",method=RequestMethod.GET)
	public List<Dept> list()
	{
		return service.list();
	}

	//调用方法出现异常后会执行的方法
	public Dept processHystrix_Get(@PathVariable("id") Long id) {
		Dept dept = new Dept();
		dept.setDname("该Id: "+ id + "没有对应的信息...");
		dept.setDeptno(id);
		dept.setDb_source("no this database in MySQL");
		return dept;
	}

}

package com.kelo.crud.controller;

import com.kelo.crud.bean.Department;
import com.kelo.crud.bean.Msg;
import com.kelo.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author KELO
 * @date 2019/5/23 - 20:01
 * 处理和部门有关的请求
 */
@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询所有部门信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/depts")
    public Msg getDepts(){
        List<Department> depts = departmentService.getDepts();
        return Msg.success().add("depts", depts);
    }

}

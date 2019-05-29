package com.kelo.crud.service;

import com.kelo.crud.bean.Department;
import com.kelo.crud.bean.Msg;
import com.kelo.crud.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author KELO
 * @date 2019/5/23 - 20:02
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> getDepts() {
        List<Department> departments = departmentMapper.selectByExample(null);
        return departments;
    }
}

package com.kelo.crud.test;

import com.kelo.crud.bean.Employee;
import com.kelo.crud.dao.DepartmentMapper;
import com.kelo.crud.dao.EmployeeMapper;
import com.kelo.crud.service.EmployeeService;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author KELO
 * @date 2019/5/20 - 22:07
 * 测试dao
 * 推荐使用Spring的单元测试，可以自动注入组件
 * 1.导入SpringTest模块
 * 2.@ContextConfiguration指定Spring配置文件的位置
 * 3.直接自动注入
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    SqlSession sqlSession;

    @Autowired
    DataSource dataSource;

    /**
     * 测试Department
     */
    @Test
    public void testCRUD(){
//        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//        Department department = ioc.getBean(Department.class);
//        System.out.println(departmentMapper);
//        System.out.println(dataSource);

        //插入部门
//        Department department = new Department(null,"开发部");
//        Department department1 = new Department(null,"测试部");
//        departmentMapper.insertSelective(department);
//        departmentMapper.insertSelective(department1);

        //生成员工
        //employeeMapper.insertSelective(new Employee(null,"Jerry","M","123@qq.com",1));

        //批量插入:可以使用批量操作的SqlSession
//        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//        for (int i = 0; i < 1000; i++) {
//            String uid = UUID.randomUUID().toString().substring(0, 5)+i;
//            mapper.insertSelective(new Employee(null,uid,"M",uid+"@163.com",1));
//        }
//        System.out.println("批量插入成功...");
        //Employee employee = employeeMapper.selectByPrimaryKeyWithDept(1);
        //System.out.println(employee);

        //批量删除
        List<Integer> ids = new ArrayList<>();
        ids.add(9);
        ids.add(10);
        ids.add(11);
        System.out.println(ids);
        employeeService.deleteBath(ids);

    }

}

package com.kelo.crud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kelo.crud.bean.Employee;
import com.kelo.crud.bean.Msg;
import com.kelo.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KELO
 * @date 2019/5/20 - 22:42
 * 处理员工CRUD请求
 */
@Controller
public class EmpController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 单个删除批量删除二合一
     * 单个 1
     * 批量 1-3
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids") String ids){
        if (ids.contains("-")){
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            for (String str_id : str_ids) {
                del_ids.add(Integer.parseInt(str_id));
            }
            employeeService.deleteBath(del_ids);
        }else{
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }
        return  Msg.success();
    }


    /**
     * 问题：
     * 请求体中有数据，但对象封装不上
     * 原因：
     * tomcat
     *    1.将请求体中的数据封装成一个map
     *    2.request.getParameter("empName")就会从这个map中取值
     *    3.SpringMVC封装POJO对象的时候，会把POJO中每个属性的值 request.getParameter("email")
     * AJAX发送PUT引发的惨案
     *    PUT请求，请求体中的数据getParameter不到
     * 配上HttpPutFormContentFilter
     * 员工更新
     * @param employee
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
    public Msg updateEmp(Employee employee){
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }

    @ResponseBody
    @RequestMapping(value = "/checkuser",method = RequestMethod.POST)
    public Msg checkuser(@RequestParam("empName") String empName){
        //先判断用户名是否是合法的表达式
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
        if (empName.matches(regx)){
            return Msg.fail().add("val_msg", "用户名必须是6-16位数字和字母组合或2-5位中文");
        }
        //数据库用户名重复校验
        boolean b = employeeService.checkUser(empName);
        if (b){
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg", "用户名不可用");
        }
    }

    /**
     * 员工保存
     * 1.支持JSR303
     * 2.需要hibernate-validator
     * @param employee
     * @return
     */
    //@ResponseBody
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    public Msg saveEmp(@Valid Employee employee, BindingResult result){
        if (result.hasErrors()){
            //校验失败
            Map<String,Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println("错误的字段名: "+error.getField());
                System.out.println("错误信息："+error.getDefaultMessage());
                map.put(error.getField(), error.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        }else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }

    }

    /**
     * 导入jackson包才可以使用@ResponseBody
     * @param pn
     * @return
     */
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        PageHelper.startPage(pn,10);
        List<Employee> emps =  employeeService.getAll();
        PageInfo pageInfo = new PageInfo(emps,5);
        return Msg.success().add("pageInfo",pageInfo);
    }

    /**
     * 查询员工数据（分页）
     * @return
     */
    //@RequestMapping(value = "/emps")
    public String getEmp(@RequestParam(value = "pn",defaultValue = "1")Integer pn,Model model){
        System.out.println("controller");
        //引入PageHelper
        //查询前只需要调用，传入当前页码和每页的记录条数
        //startPage后面紧跟着查询就是一个分页查询
        PageHelper.startPage(pn,10);
        List<Employee> emps =  employeeService.getAll();
        PageInfo pageInfo = new PageInfo(emps,5);
        //使用PageInfo包装查询后的结果,只需要将PageInfo交给页面就行了
        //其中封装了详细的分页信息，包括查出来的数据,传入连续显示的页数
        model.addAttribute("pageInfo", pageInfo);

        return "/list";


    }

}

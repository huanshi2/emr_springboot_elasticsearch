package com.hs2.emr_springboot_elasticsearch.controller;

import com.hs2.emr_springboot_elasticsearch.dto.EmployeeDTO;
import com.hs2.emr_springboot_elasticsearch.service.ESService;
import com.hs2.emr_springboot_elasticsearch.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    @Resource
    private ESService esService;

    @RequestMapping("/rangeQuery")
    public List<EmployeeDTO> rangeQuery(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.rangeQuery(employeeVO);
    }

    @RequestMapping("/ExistQuery")
    public List<EmployeeDTO> ExistQuery(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.ExistQuery(employeeVO);
    }

    @RequestMapping("/queryAll")
    public List<EmployeeDTO> queryAll() {
        return esService.queryAll();
    }

    @RequestMapping("/delete")
    public List<EmployeeDTO> delete(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.queryAll();
    }

    @RequestMapping("/updateData")
    public List<EmployeeDTO> update(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.update(employeeVO);
    }


}

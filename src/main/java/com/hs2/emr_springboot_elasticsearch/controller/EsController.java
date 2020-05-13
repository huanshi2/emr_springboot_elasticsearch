package com.hs2.emr_springboot_elasticsearch.controller;

import com.hs2.emr_springboot_elasticsearch.dto.EmployeeDTO;
import com.hs2.emr_springboot_elasticsearch.service.ESService;
import com.hs2.emr_springboot_elasticsearch.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    @Resource
    private ESService esService;

    @RequestMapping(value = "/CreateIndex")
    public String CreateIndex(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.CreateIndex(employeeVO);
    }

    @RequestMapping("/rangeQuery")
    public List<EmployeeDTO> RangeQuery(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.RangeQuery(employeeVO);
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

    @RequestMapping("/update")
    public List<EmployeeDTO> update(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.update(employeeVO);
    }

    @RequestMapping("/addData")
    public List<EmployeeDTO> addData(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.addData(employeeVO);
    }


}

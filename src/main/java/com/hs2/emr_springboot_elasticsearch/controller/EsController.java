package com.hs2.emr_springboot_elasticsearch.controller;

import com.hs2.emr_springboot_elasticsearch.dto.EmployeeDTO;
import com.hs2.emr_springboot_elasticsearch.entity.User;
import com.hs2.emr_springboot_elasticsearch.service.ESService;
import com.hs2.emr_springboot_elasticsearch.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @RequestMapping(value = "/DeleteIndex")
    public String DeleteIndex(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.DeleteIndex(employeeVO);
    }

    @RequestMapping(value = "/SearchQuery")
    public List<EmployeeDTO> SearchQuery(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.SearchQuery(employeeVO);
    }

//    @RequestMapping("/AddData")
//    public Map<String, Object> AddData(@RequestParam(required = false) EmployeeVO employeeVO) {
//        Map<String, Object> modelMap = new HashMap<>();
//
//        EmployeeVO user = new EmployeeVO();
//        user.setIndex(employeeVO.getIndex());
//        user.setType(employeeVO.getType());
//        user.setId(employeeVO.getId());
//        user.setName(employeeVO.getName());
//        user.setSex(employeeVO.getSex());
//        user.setMessage(employeeVO.getMessage());
//
//        esService.AddData(user);
//
//        modelMap.put("success", false);
//
//        return modelMap;
//    }

    @RequestMapping("/AddData")
    public String addData(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.AddData(employeeVO);
    }

    @RequestMapping("/UpdateData")
    public String UpdateData(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.UpdateData(employeeVO);
    }

    @RequestMapping("/DeleteData")
    public String DeleteData(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.DeleteData(employeeVO);
    }

    @RequestMapping("/RangeQuery")
    public List<EmployeeDTO> RangeQuery(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.RangeQuery(employeeVO);
    }

    @RequestMapping("/ExistQuery")
    public List<EmployeeDTO> ExistQuery(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.ExistQuery(employeeVO);
    }

    @RequestMapping("/queryAll")
    public List<EmployeeDTO> queryAll(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.queryAll(employeeVO);
    }

    @RequestMapping("/delete")
    public List<EmployeeDTO> delete(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.queryAll(employeeVO);
    }


}

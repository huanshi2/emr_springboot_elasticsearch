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

    //增
    @RequestMapping(value = "/CreateIndex")
    public String CreateIndex(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.CreateIndex(employeeVO);
    }

    @RequestMapping("/AddData")
    public String addData(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.AddData(employeeVO);
    }

    //删
    @RequestMapping(value = "/DeleteIndex")
    public String DeleteIndex(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.DeleteIndex(employeeVO);
    }

    @RequestMapping("/DeleteData")
    public String DeleteData(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.DeleteData(employeeVO);
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

    //改
    @RequestMapping("/UpdateData")
    public String UpdateData(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.UpdateData(employeeVO);
    }

    //查
    @RequestMapping("/queryAll")
    public List<EmployeeDTO> queryAll(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.queryAll(employeeVO);
    }

    @RequestMapping(value = "/SearchQuery")
    public List<EmployeeDTO> SearchQuery(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.SearchQuery(employeeVO);
    }

    @RequestMapping("/MatchSearch")
    public List<EmployeeDTO> MatchSearch(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.MatchSearch(employeeVO);
    }

    @RequestMapping("/MutimatchSearch")
    public List<EmployeeDTO> MutimatchSearch(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.MutimatchSearch(employeeVO);
    }

    @RequestMapping("/RangeQuery")
    public List<EmployeeDTO> RangeQuery(@RequestBody(required = false) EmployeeVO employeeVO) {
        return esService.RangeQuery(employeeVO);
    }




}

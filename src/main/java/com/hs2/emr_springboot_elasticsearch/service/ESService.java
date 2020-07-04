package com.hs2.emr_springboot_elasticsearch.service;

import com.hs2.emr_springboot_elasticsearch.dto.EmployeeDTO;
import com.hs2.emr_springboot_elasticsearch.vo.EmployeeVO;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


public interface ESService {

    //增
    String CreateIndex(EmployeeVO employeeVO);
    String AddData(EmployeeVO employeeVO);

    //删
    String DeleteIndex(EmployeeVO employeeVO);
    String DeleteData(EmployeeVO employeeVO);

    //改
    String UpdateData(EmployeeVO employeeVO);


    //查
    List<EmployeeDTO> queryAll(EmployeeVO employeeVO);

    List<EmployeeDTO> SearchQuery(EmployeeVO employeeVO);

    List<EmployeeDTO> MatchSearch(EmployeeVO employeeVO);

    List<EmployeeDTO> MutimatchSearch(EmployeeVO employeeVO);

    List<EmployeeDTO> RangeQuery(EmployeeVO employeeVO);


}

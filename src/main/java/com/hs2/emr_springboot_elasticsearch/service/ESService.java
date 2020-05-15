package com.hs2.emr_springboot_elasticsearch.service;

import com.hs2.emr_springboot_elasticsearch.dto.EmployeeDTO;
import com.hs2.emr_springboot_elasticsearch.vo.EmployeeVO;

import java.util.List;

public interface ESService {

    List<EmployeeDTO> ExistQuery(EmployeeVO employeeVO);

    List<EmployeeDTO> RangeQuery(EmployeeVO employeeVO);

    List<EmployeeDTO> queryAll();

    String CreateIndex(EmployeeVO employeeVO);

    String DeleteIndex(EmployeeVO employeeVO);

    String AddData(EmployeeVO employeeVO);

    String UpdateData(EmployeeVO employeeVO);


}

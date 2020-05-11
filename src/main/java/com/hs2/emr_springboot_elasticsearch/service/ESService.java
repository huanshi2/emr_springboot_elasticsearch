package com.hs2.emr_springboot_elasticsearch.service;

import com.hs2.emr_springboot_elasticsearch.dto.EmployeeDTO;
import com.hs2.emr_springboot_elasticsearch.vo.EmployeeVO;

import java.util.List;

public interface ESService {

    List<EmployeeDTO> query(EmployeeVO employeeVO);

    List<EmployeeDTO> queryAll();

}

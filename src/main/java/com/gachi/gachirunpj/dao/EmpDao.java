package com.gachi.gachirunpj.dao;

import com.gachi.gachirunpj.dto.EmpDto;

public interface EmpDao {
    int empSignUp(EmpDto emp);

    int checkEmpId(String emp_id);
}

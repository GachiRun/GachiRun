package com.gachi.gachirunpj.dao;

import com.gachi.gachirunpj.dto.EmpDto;

public interface EmpDao {
    int empSignUp(EmpDto emp);

    int checkEmpId(String emp_id);

    EmpDto getInfo(EmpDto emp);

    EmpDto getInfoNum(int empNum);

    int changePw(String empPasswd, int empNum);

    EmpDto login(String username);
}

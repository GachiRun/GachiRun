package com.gachi.gachirunpj.service;

import com.gachi.gachirunpj.dao.EmpDao;
import com.gachi.gachirunpj.dto.EmpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpService {
    private final EmpDao ed;

    public int empSignUp(EmpDto emp)
    {
        System.out.println("EmpService empSave Start");
        int result = ed.empSignUp(emp);
        return result;
    }

    public int checkEmpId(String emp_id) {
        System.out.println("EmpService empSave Start");
        int result = ed.checkEmpId(emp_id);
        return result;
    }
}

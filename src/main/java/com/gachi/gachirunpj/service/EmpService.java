package com.gachi.gachirunpj.service;

import com.gachi.gachirunpj.dao.EmpDao;
import com.gachi.gachirunpj.dto.EmpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpService {
    private final EmpDao ed;

    public EmpDto login(String username) {
        EmpDto emp = new EmpDto();
        try {
            emp = ed.login(username);
        } catch (Exception e) {
            System.out.println("login error" + e.getMessage());
        }

        return emp;
    }

    public int empSignUp(EmpDto emp)
    {
        int result = ed.empSignUp(emp);
        return result;
    }

    public int checkEmpId(String emp_id) {
        int result = ed.checkEmpId(emp_id);
        return result;
    }


    public EmpDto getInfo(EmpDto chkEmp) {
        EmpDto emp = new EmpDto();

        try {
            emp = ed.getInfo(chkEmp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return emp;
    }

    public EmpDto getInfoNum(int empNum) {
        EmpDto emp = new EmpDto();

        try {
            emp = ed.getInfoNum(empNum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return emp;
    }

    public int changePw(String empPasswd, int empNum) {
        int result = 0;
        try {
            result = ed.changePw(empPasswd, empNum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}

package com.gachi.gachirunpj.service;

import com.gachi.gachirunpj.dao.EmpDao;
import com.gachi.gachirunpj.dto.EmpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpService {
    private final EmpDao ed;

    public EmpDto login(String emp_id) {
        System.out.println("EmpService login Start");
        EmpDto emp = new EmpDto();
        try {
            emp = ed.login(emp_id);
        } catch (Exception e) {
            System.out.println("login error" + e.getMessage());
        }

        return emp;
    }

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


    public EmpDto getInfo(EmpDto chkEmp) {
        System.out.println("EmpService getInfo Start");
        EmpDto emp = new EmpDto();

        try {
            emp = ed.getInfo(chkEmp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return emp;
    }

    public EmpDto getInfoNum(int empNum) {
        System.out.println("EmpService getInfoNum Start");
        EmpDto emp = new EmpDto();

        try {
            emp = ed.getInfoNum(empNum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return emp;
    }

    public int changePw(String empPasswd, int empNum) {
        System.out.println("EmpService changePw Start");
        System.out.println("EmpService changePw emp_passwd: "+empPasswd);
        System.out.println("EmpService changePw emp_num: "+empNum);
        int result = 0;
        try {
            result = ed.changePw(empPasswd, empNum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}

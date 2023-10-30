package com.gachi.gachirunpj.dao;

import com.gachi.gachirunpj.dto.EmpDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {
    // Mybatis DB 연동
    private final SqlSession session;

    @Override
    public int empSignUp(EmpDto emp) {
        int result = 0;
        try {
            result = session.insert("wsEmpInsert", emp);
            result++;
        } catch (Exception e) {
            System.out.println("EmpDaoImpl empSave Error: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int checkEmpId(String emp_id) {
        int result = 0;
        List<EmpDto> emplist = new ArrayList<EmpDto>();

        try {
            emplist = session.selectList("checkEmpId", emp_id);
            result = emplist.size();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return result;
    }

    @Override
    public EmpDto getInfo(EmpDto chkEmp) {
        EmpDto emp = new EmpDto();
        if (chkEmp.getEmp_id() == null){
            try {
                emp = session.selectOne("getUserInfo", chkEmp);
                if (emp == null) {
                    emp = new EmpDto();
                    emp.setResult(0);
                    emp.setMsg("아이디 찾기에 실패하셨습니다.");
                }
            } catch (Exception e) {
                System.out.println("EmpDaoImpl getInfo 에러 발생" + e.getMessage());
            }
        }
        else {
            try {
                emp = session.selectOne("getUserPWInfo", chkEmp);
                if (emp == null) {
                    emp = new EmpDto();
                    emp.setResult(0);
                    emp.setMsg("비번 찾기에 실패하셨습니다.");
                }
            } catch (Exception e) {
                System.out.println("EmpDaoImpl getInfo 에러 발생" + e.getMessage());
            }
        }
        return emp;
    }

    @Override
    public EmpDto getInfoNum(int empNum) {

        EmpDto emp = new EmpDto();

        try {
            emp = session.selectOne("getUserInfoNum", empNum);
            if (emp == null) {
                emp = new EmpDto();
                emp.setResult(0);
                emp.setMsg("비번 찾기에 실패하셨습니다.");
            }
        } catch (Exception e) {
            System.out.println("EmpDaoImpl getInfo 에러 발생" + e.getMessage());
        }
        return emp;
    }

    @Override
    public int changePw(String empPasswd, int empNum) {
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("Emp_passwd", empPasswd);
        map.put("Emp_num", empNum);

        try {
            result = session.update("changePw", map);
        } catch (Exception e) {
            System.out.println("EmpDaoImpl changePw 에러 발생"+ e.getMessage());
        }
        return result;
    }

    @Override
    public EmpDto login(String username) {
        EmpDto emp = new EmpDto();

        try {
            emp = session.selectOne("chkLogin", username);
        } catch (Exception e) {
            System.out.println("EmpDaoImpl login 에러 발생"+ e.getMessage());
        }

        return emp;
    }


}

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
            System.out.println("EmpDaoImpl empSave Start");
            System.out.println("test getEmp_gender: " + emp.getEmp_gender());
            System.out.println("test getEmp_id: " + emp.getEmp_id());
            System.out.println("test getEmp_name: " + emp.getEmp_name());
            System.out.println("test getEmp_name: " + emp.getEmp_Nickname());
            System.out.println("test getEmp_name: " + emp.getEmp_passwd());
            System.out.println("test getEmp_name: " + emp.getEmp_Email());
            System.out.println("test getEmp_name: " + emp.getEmp_birth());
            System.out.println("test getEmp_name: " + emp.getEmp_phone());
            System.out.println("test getEmp_name: " + emp.getEmp_gender());


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
        System.out.println("EmpDaoImpl checkEmpId Start");
        System.out.println("EmpDaoImpl emp_id ->"+ emp_id);

        try {
            emplist = session.selectList("checkEmpId", emp_id);
            result = emplist.size();
            System.out.println("checkEmpId 쿼리문 결과->"+result);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return result;
    }

    @Override
    public EmpDto getInfo(EmpDto chkEmp) {
        System.out.println("EmpDaoImpl getInfo Start");
        EmpDto emp = new EmpDto();
        System.out.println("EmpDaoImpl getEmp_name"+chkEmp.getEmp_name());
        System.out.println("EmpDaoImpl getEmp_Email"+chkEmp.getEmp_Email());
        System.out.println("EmpDaoImpl getEmp_phone"+chkEmp.getEmp_phone());
        System.out.println("EmpDaoImpl getEmp_phone"+chkEmp.getEmp_id());
        if (chkEmp.getEmp_id() == null){
            System.out.println("EmpDaoImpl getEmp_id 는 null이다");
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
            System.out.println("EmpDaoImpl getEmp_id 는 null이 아니다");
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

        System.out.println("결과 값: "+ emp.getEmp_name());
        return emp;
    }

    @Override
    public EmpDto getInfoNum(int empNum) {
        System.out.println("EmpDaoImpl getInfoNum Start");

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
        System.out.println("결과 값: "+ emp.getEmp_name());
        return emp;
    }

    @Override
    public int changePw(String empPasswd, int empNum) {
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("Emp_passwd", empPasswd);
        map.put("Emp_num", empNum);

        System.out.println("map->"+map);

        try {
            result = session.update("changePw", map);
        } catch (Exception e) {
            System.out.println("EmpDaoImpl changePw 에러 발생"+ e.getMessage());
        }
        return result;
    }

    @Override
    public EmpDto login(String empId) {
        return null;
    }


}

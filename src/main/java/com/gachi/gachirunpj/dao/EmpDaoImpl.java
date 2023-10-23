package com.gachi.gachirunpj.dao;

import com.gachi.gachirunpj.dto.EmpDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
}

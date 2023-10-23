package com.gachi.gachirunpj.controller;

import com.gachi.gachirunpj.dto.EmpDto;
import com.gachi.gachirunpj.service.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class authcontroller {
        //전체 유저의 Controller
        private final EmpService empService;
        //암호화 하기 위한 객체 선언
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    ///////회원가입///////
    @PostMapping("/empSignUp")
    public String empSignUp(EmpDto emp, @RequestParam String emp_name, @RequestParam String emp_id, @RequestParam String emp_passwd, @RequestParam String emp_nickname){
        System.out.println("EmpController empSignUp Start");
        System.out.println("EmpController EnCrypt Start");

        String securePasswd = encoder.encode(emp.getEmp_passwd()); //비밀 번호 암호화 작업
        emp.setEmp_passwd(securePasswd);
        emp.setEmp_id(emp_id);
        emp.setEmp_name(emp_name);
        emp.setEmp_Nickname(emp_nickname);
        System.out.println("암호화된 비밀번호: "+securePasswd);
        System.out.println("아이디: "+emp_id);
        System.out.println("이름: "+emp_name);
        System.out.println("닉네임: "+emp_nickname);

        System.out.println("EmpController empSave Start...");
        int result = empService.empSignUp(emp);

        if (result>0) //저장이 성공한 경우
        {
            System.out.println("Emp 테이블 직원 저장 완료");
        }
        else //저장이 실패한 경우
        {
            System.out.println("Emp 테이블 직원 저장 실패");
        }
        return "loginForm.html";
    }

    //아이디 중복 확인//
    @ResponseBody
    @PostMapping("/checkEmpId")
    public int checkEmpId(String emp_id) {
        System.out.println("empController checkEmpId Start");
        int result = empService.checkEmpId(emp_id);
        String check = "";

        if(result > 0) //아이디가 중복되는 경우
        {
            check = "중복";
        }
        else	//아이디가 중복되지 않는 경우
        {
            check = "사용 가능";
        }

        System.out.println(check); //로그용 메시지 출력


        return result;
    }
}

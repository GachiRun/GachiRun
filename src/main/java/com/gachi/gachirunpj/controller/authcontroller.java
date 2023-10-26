package com.gachi.gachirunpj.controller;

import com.gachi.gachirunpj.dto.EmpDto;
import com.gachi.gachirunpj.service.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class authcontroller {
        //전체 유저의 Controller
        private final EmpService empService;
        //암호화 하기 위한 객체 선언
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();




    //////////////////////////////////////아이디, 비번 찾기//////////////////////////////
    //아이디 찾기 메소드 [AJAX]
    @ResponseBody
    @RequestMapping("/findId")
    public EmpDto findId( @RequestParam String emp_name, @RequestParam String emp_email, @RequestParam int emp_phone)
    {
        System.out.println("EmpService login Start");
        EmpDto emp = new EmpDto();
        EmpDto chkEmp = new EmpDto();
        System.out.println("Parameter Test 1 email : "+emp_email);
        System.out.println("Parameter Test 2 Name : "+emp_name);
        System.out.println("Parameter Test 3 phone : "+emp_phone);
        chkEmp.setEmp_Email(emp_email);
        chkEmp.setEmp_name(emp_name);
        chkEmp.setEmp_phone(emp_phone);
        System.out.println("Parameter Test 1 email : "+chkEmp.getEmp_Email());
        System.out.println("Parameter Test 2 Name : "+chkEmp.getEmp_name());
        System.out.println("Parameter Test 3 phone : "+chkEmp.getEmp_phone());
        try {
            emp = empService.getInfo(chkEmp); //찾기를 원하는 아이디의 사번을 가져온다.
            System.out.println("쿼리문 실행 후 ");
            System.out.println("emp Parameter Test 1 email : "+ emp.getEmp_Email());
            System.out.println("emp Parameter Test 2 Name : "+ emp.getEmp_name());
            System.out.println("emp Parameter Test 3 phone : "+ emp.getEmp_phone());

            //찾으려는 사번에 해당하는 유저의 이름과 이메일 일치한 경우
            if(emp_name.equals(emp.getEmp_name())&& emp_email.equals(emp.getEmp_Email()) && emp_phone == emp.getEmp_phone())
            {
                System.out.println("정보가 모두 일치 되었습니다.");
                System.out.println("당신의 아이디는 " + emp.getEmp_id() + " 입니다.");
                emp.setMsg(emp.getEmp_name()+"의 아이디는 "+emp.getEmp_id()+" 입니다."); //결과 메시지에 저장한다.
                emp.setResult(1); //결과 값을 저장한다.
            } else {
                System.out.println("정보가 일치하지 않습니다."); //찾으려는 사번에 해당하는 유저의 이름과 이메일 일치하지 않은 경우
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return emp;
    }

    //비밀번호 찾기 메소드 [AJAX]
    @ResponseBody
    @RequestMapping("/findPw")
    public EmpDto findPw( @RequestParam String emp_name, @RequestParam String emp_id, @RequestParam String emp_email,  @RequestParam int emp_phone)
    {
        System.out.println("EmpService login Start");
        EmpDto emp = new EmpDto();
        EmpDto chkEmp = new EmpDto();
        System.out.println("Parameter Test 1 email : "+emp_email);
        System.out.println("Parameter Test 2 Name : "+emp_name);
        System.out.println("Parameter Test 3 phone : "+emp_phone);
        System.out.println("Parameter Test 4 id : "+emp_id);

        chkEmp.setEmp_Email(emp_email);
        chkEmp.setEmp_name(emp_name);
        chkEmp.setEmp_phone(emp_phone);
        chkEmp.setEmp_id(emp_id);

        try {
            //해당하는 유저 정보를 호출한다.
            emp = empService.getInfo(chkEmp);

            // 이름 & 이메일 & 아이디를 찾아온 유저 정보와 일치한 경우
            if(emp_name.equals(emp.getEmp_name()) && emp_id.equals(emp.getEmp_id())&& emp_email.equals(emp.getEmp_Email()) && emp_phone == emp.getEmp_phone() )
            {
                System.out.println("정보가 모두 일치 되었습니다.");
                System.out.println("당신의 아이디는 " + emp.getEmp_id() + " 입니다.");
                emp.setMsg("비밀 번호를 수정합니다. <br><h5>비밀 번호를 입력해주세요</h5></br>"); //결과 메시지를 저장한다.
                emp.setResult(1); //결과 값을 저장한다.
            }
            else { // 일치하지 않은 경우
                System.out.println("정보가 일치하지 않습니다.");
                emp.setMsg("비밀번호 찾기에 실패하셨습니다.");
                emp.setResult(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return emp;
    }

    @ResponseBody
    @RequestMapping("/changePw") //비밀번호 찾기에 비밀번호 변동이 생긴 경우  [AJAX]
    public int changePw(@RequestParam String emp_password, @RequestParam int emp_num)
    {
        int result = 0;
        System.out.println("EmpService changePw Start");

        System.out.println("emp_num : "+ emp_num);
        System.out.println("emp_passwd : "+emp_password);

        EmpDto emp = new EmpDto();
        try {
            emp = empService.getInfoNum(emp_num); //해당 정보 가져오기
            emp.setResult(1);
            System.out.println("암호화된 비밀번호: "+encoder.encode(emp_password));
            emp.setEmp_passwd(encoder.encode(emp_password)); //새로 입력값으로 받아온 비밀번호 암호화
            result = empService.changePw(emp.getEmp_passwd(),emp_num); //비밀번호 변동에 대한 결과값 저장

        } catch (Exception e) {
            System.out.println(e.getMessage());
            emp.setResult(0);
        }


        return result;
    }

    ///////회원가입///////
    @PostMapping("/empSignUp")
    public String empSignUp(EmpDto emp, @RequestParam String emp_name, @RequestParam String emp_id, @RequestParam String emp_passwd, @RequestParam String emp_nickname, @RequestParam String emp_email, @RequestParam String emp_birth, @RequestParam String emp_gender,@RequestParam int emp_phone ){
        System.out.println("EmpController empSignUp Start");
        System.out.println("EmpController EnCrypt Start");

        String securePasswd = encoder.encode(emp.getEmp_passwd()); //비밀 번호 암호화 작업
        emp.setEmp_passwd(securePasswd);
        emp.setEmp_id(emp_id);
        emp.setEmp_name(emp_name);
        emp.setEmp_Nickname(emp_nickname);
        emp.setEmp_Email(emp_email);

        System.out.println("암호화된 비밀번호: "+securePasswd);
        System.out.println("아이디: "+emp_id);
        System.out.println("이름: "+emp_name);
        System.out.println("닉네임: "+emp_nickname);
        System.out.println("메일: "+emp_email);
        System.out.println("생일: "+emp_birth);
        System.out.println("성별: "+emp_gender);
        System.out.println("전화번호: "+emp_phone);

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

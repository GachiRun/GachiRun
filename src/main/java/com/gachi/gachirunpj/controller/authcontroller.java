package com.gachi.gachirunpj.controller;

import com.gachi.gachirunpj.auth.PrincipalDetails;
import com.gachi.gachirunpj.dto.EmpDto;
import com.gachi.gachirunpj.service.EmpService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
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
        chkEmp.setEmp_Email(emp_email);
        chkEmp.setEmp_name(emp_name);
        chkEmp.setEmp_phone(emp_phone);
        try {
            emp = empService.getInfo(chkEmp); //찾기를 원하는 아이디의 사번을 가져온다.

            //찾으려는 사번에 해당하는 유저의 이름과 이메일 일치한 경우
            if(emp_name.equals(emp.getEmp_name())&& emp_email.equals(emp.getEmp_Email()) && emp_phone == emp.getEmp_phone())
            {
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
        EmpDto emp = new EmpDto();
        EmpDto chkEmp = new EmpDto();

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

        EmpDto emp = new EmpDto();
        try {
            emp = empService.getInfoNum(emp_num); //해당 정보 가져오기
            emp.setResult(1);
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

        String securePasswd = encoder.encode(emp.getEmp_passwd()); //비밀 번호 암호화 작업
        emp.setEmp_passwd(securePasswd);
        emp.setEmp_id(emp_id);
        emp.setEmp_name(emp_name);
        emp.setEmp_Nickname(emp_nickname);
        emp.setEmp_Email(emp_email);
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


    ////////////////////////////로그인 성공여부/////////////////////
    //로그인 메소드 [수정]
//		@ResponseBody




    //관리자이니 일반 유저인지 확인하기 위한 메소드
/*    @GetMapping("/auth_finder")
    public String auth_finder(HttpSession session,Model model)
    {
        Emp emp = (Emp) session.getAttribute("emp"); //"로그인시" 세션을 설정해 놓은 값을 가져온다.

        EmpForSearch empForSearch = (EmpForSearch) session.getAttribute("empForSearch"); //JPA 외래키를 설정해 놓은 값을 받아오기 위해서 조회용 객체에 저장한 세션값을 가져온다.

        System.out.println(emp.getEmp_name()+"님은 "+empForSearch.getAuth_name()+" 입니다");

        //관리자 일 경우
        if(empForSearch.getAuth_num() == 0)
        {
            model.addAttribute("emp",emp);
            model.addAttribute("empForSearch",empForSearch);
            return "/main/user/Main";
        }
        //일반 유저 일 경우
        else
        {
            model.addAttribute("emp",emp);
            model.addAttribute("empForSearch",empForSearch);
            return "/main/user/Main";
        }
    }*/

    //로그아웃
    @RequestMapping("/logoutForm")
    public String logout(@AuthenticationPrincipal PrincipalDetails principal, HttpSession session)
    {
        System.out.println("session" + session);
        session.invalidate();
        return "emp/logoutForm";
    }

    //마이페이지
    @RequestMapping("/MyPage")
    public String myPageForm(@AuthenticationPrincipal PrincipalDetails principal, HttpSession session,Model model)
    {
        session.setAttribute("emp", principal.getEmpDto()); // 세션 적용
        model.addAttribute("emp", principal.getEmpDto());//원래는 위 세션으로 넘기는데 안넘어가지길래 만든 테스트 model

        return "user/MyPage";
    }




}

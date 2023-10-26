package com.gachi.gachirunpj.dto;

import lombok.Data;

@Data
public class EmpDto {
    public int Emp_num;
    public String Emp_id;
    public String Emp_passwd;
    public String Emp_name;
    public String Emp_Nickname;
    public String Emp_Hire_Date;
    public String Emp_Edit_date;
    public String Emp_Email;
    public String Emp_birth;
    public String Emp_gender;
    public int    Emp_phone;
    public String Emp_Social;
    public int    Crew_Num;
    public int    State_Num;

    //리뷰용
    private String msg; //결과 메시지
    private int result; //결과 상태창
}

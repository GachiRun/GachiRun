<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!DOCTYPE html>
<%
    String context = request.getContextPath();
%>
<html>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">

    var id_check = false;

    //자동으로 도메인값을 넣어주는 함수
    function changeDomain()
    {
        if($('#domain_list').val() == 'type')
        {
            $('#user_email2').val("");
            $('#user_email2').attr("readonly", false);
        }
        else
        {
            $('#user_email2').val($('#domain_list').val());
            $('#user_email2').attr("readonly", true);
        }
    }

    //회원 가입 유효성 체크
    function updateUpCheck()
    {
        var check = true;
        var address = $('#postcode').val() + " " + $('#address').val() + " /" + $('#detailAddress').val();
        var email = $('#user_email1').val() + "@" +  $('#user_email2').val()

        $('#emp_address').attr('value', address);
        $('#emp_email').attr('value', email);

        if($('#emp_password').val() != $('#chk_emp_password').val())
        {
            alert('패스워드가 일치하지 않습니다. 다시 입력해주세요.');
            check = false;
        }

        if($('#emp_password').val().length < 8)
        {
            alert('패스워드는 보안상의 이유로 8글자 이상 입력해주세요');
            check = false;
        }

        if(check == false)
        {
            alert("체크 항목들을 확인해 주세요");
            return false;
        }
    }


    $(function()
    {
        //이메일 값 나누기
        var email = '${emp.emp_email}';
        var user_email = email.split('@');
        var user_email1 = user_email[0];
        var user_email2 = user_email[1];

        $('#user_email1').attr('value', user_email1);
        $('#user_email2').attr('value', user_email2);

        //주소 값 불러온 후 나눠서 넣기
        var address1 = '${emp.emp_address}';
        var index1 = address1.indexOf(' ');
        var index2 = address1.indexOf('/');

        var postcode = address1.substr(0, index1);
        var address =  address1.substr(index1,index2-index1);
        var detailAddress = address1.substr(index2+1);

        $('#postcode').attr('value', postcode);
        $('#address').attr('value', address);
        $('#detailAddress').attr('value', detailAddress);

        if('${result}' == 1)
        {
            alert("회원 정보 변경에 성공했습니다.");
        }
        else if('${result}' == 2)
        {
            alert("회원 정보 변경에 실패했습니다.");
        }

    });
</script>
<head>
    <style type="text/css">
        .form-control {
            display: inline;
            width: 50%;
            height: calc(1.5em + .75rem + 2px);
            padding: .375rem .75rem;
            font-size: 1rem;
            font-weight: 400;
            line-height: 1.5;
            color: #6e707e;
            background-color: #fff;
            background-clip: padding-box;
            border: 1px solid #d1d3e2;
            border-radius: .35rem;
            -webkit-transition: border-color .15s ease-in-out, -webkit-box-shadow
            .15s ease-in-out;
        }

        #postcode {
            width: 300px;
        }

    </style>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>

<div class="container-xxl flex-grow-1 container-p-y" style="width: 1600px;">
    <h4 class="fw-bold py-3 mb-4">마이페이지jsp</h4>
    <div class="card mb-6">

        <form action="/user/editInfo" method="post">
            <table class="table" style="width: 1300px; margin-left:50px;  margin-top: 50px; margin-bottom: 50px;">
                <tr>
                    <th><label for="emp_name">이름</label></th>
                    <td><input type="text"  class="form-control" id="emp_name" name="emp_name" value="${emp.Emp_name}"}></td>
                </tr>
                <tr>
                    <th><label for="emp_id">아이디</label></th>
                    <td><input type="text"   class="form-control" readonly tabindex="-1" id="emp_id" name="emp_id" value="${emp.Emp_id}"}></td>
                </tr>
                <tr>
                    <th><label for="emp_password">비밀번호</label></th>
                    <td><input type="password"  class="form-control" id="emp_password" name="emp_password" value="${emp.Emp_passwd}"}></td>
                </tr>
                <tr>
                    <th><label for="chk_emp_password">비밀번호 재확인</label></th>
                    <td><input type="password"  class="form-control" id="chk_emp_password" name="chk_emp_password" value="${emp.Emp_passwd}"}></td>
                </tr>



            </table>

            <div style=" text-align: center;">
                <button type="submit" style="display: inline-block;" class="btn btn-primary btn-lg" onclick="updateUpCheck()">수정하기</button>
            </div>

            <input type="hidden" id="emp_address" name ="emp_address">
            <input type="hidden" id="emp_email" name = "emp_email">
        </form>
    </div>
</div>
</body>
</html>

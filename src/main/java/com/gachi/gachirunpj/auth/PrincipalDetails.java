package com.gachi.gachirunpj.auth;

import java.util.ArrayList;
import java.util.Collection;

import com.gachi.gachirunpj.dto.EmpDto;
import com.gachi.gachirunpj.dto.SecurityUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
// Authentication 객체에 저장할 수 있는 유일한 타입
//login이라는 주소가 호출이 되면, Security가 낚아 채서 login 진행
//login 진행 완료후 Security Session 생성 (Security ContextHolder에 정보 저장)
//Security Session -> Authentication -> UserDetails[PrincipalDetails]
@Data
public class PrincipalDetails implements UserDetails {
	
	private SecurityUserDTO securityUser;
	private EmpDto empDto;

	public PrincipalDetails(SecurityUserDTO securityUser, EmpDto empDto) {
		this.securityUser = securityUser;
		this.empDto = empDto;
	}
	
	@Override
	// 해당 User의 권한을 리턴하는 곳.
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return securityUser.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return securityUser.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return securityUser.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 예시 -> 1년동안 Login안한 사람 휴면 계정 -> return false
		return true;
	}

}


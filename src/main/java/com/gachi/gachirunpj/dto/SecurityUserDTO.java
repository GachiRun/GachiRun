
package com.gachi.gachirunpj.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@RequiredArgsConstructor
public class SecurityUserDTO{
	private String username;
	private String password;
	private String role;
	
	public SecurityUserDTO(String username, String password, String role) {
		log.info("SecurityUser member.username = {}", username);
	    log.info("SecurityUser member.password = {}", password);
	    log.info("SecurityUser member.role = {}",  role);
	   
		this.username = username;
		this.password = password;
		this.role = role;
	}
}


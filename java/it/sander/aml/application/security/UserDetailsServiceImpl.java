package it.sander.aml.application.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if(username.equals("admin")) {
			ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
			list.add(new SimpleGrantedAuthority("GPR_READ"));
			list.add(new SimpleGrantedAuthority("GPR_WRITE"));
			return new AmlUserDetailsImpl(new User(username, passwordEncoder.encode("password"), list));
		}
		
		if(username.equals("sander")) {
			ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
			list.add(new SimpleGrantedAuthority("GPR_READ"));
			return new AmlUserDetailsImpl(new User(username, passwordEncoder.encode("password"), list));
		}
		
		return null;
	}

}

package edge.core.modules.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtil {
	
	public static Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		
	public SpringSecurityUtil() {
		
	}
	
	public static String encodePassword(String currPassword, String salt) {
		return passwordEncoder.encodePassword(currPassword, salt);
	}
	
	public static boolean isAdmin(){
		boolean result = false;
		
		List<String> roles = getLoggedInRoles();
		
		if(roles != null){
			for(String role : roles){
				if(role.contains("ADMIN")){
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	
	public static List<String> getLoggedInRoles() {
		List<String> roles = null;
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		if(authorities != null){
			for (GrantedAuthority grantedAuthority : authorities) {
				roles.add(new String(grantedAuthority.getAuthority().getBytes()));
			}			
		}
		return roles;
	}
	
}

package emgc.randomlunch.security.config;

import emgc.randomlunch.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class RoleConfig {

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(Role.getRoleHierarchy());
        return roleHierarchy;
    }

    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter(){
        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
        return roleHierarchyVoter;
    }

    public void setResourceRoles(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/question/**").permitAll()
                .antMatchers("/thumbnail/upload").hasRole("ADMIN")
                .antMatchers("/thumbnail/**").permitAll()
                .antMatchers("/user/countrycode").permitAll()
                .antMatchers("/user/join").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/**").hasRole(Role.USER.getRole())
                .antMatchers("/**/list").permitAll()
                .antMatchers("/**").hasRole(Role.ADMIN.getRole())
                .anyRequest().authenticated();
    }


}

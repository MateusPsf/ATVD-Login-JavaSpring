package br.com.conectasaude.loginseguro.config;

import br.com.conectasaude.loginseguro.service.MongoUserDetailsService;
import br.com.conectasaude.loginseguro.service.LogAuditoriaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LogAuditoriaService logs) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/img/**", "/cadastro", "/login").permitAll()
                        .requestMatchers("/aluno", "/aluno/**").hasRole("ALUNO")
                        .requestMatchers("/professor", "/professor/**").hasAnyRole("PROFESSOR", "ADMIN")
                        .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .successHandler((request, response, authentication) -> {
                            logs.registrar("INFO", "LOGIN_SUCESSO", "Autenticação realizada com sucesso.", authentication.getName());
                            response.sendRedirect(request.getContextPath() + "/painel");
                        })
                        .failureHandler((request, response, exception) -> {
                            // Só registra o identificador mascarado; nunca senha, exceção ou corpo do formulário.
                            String identificador = request.getParameter("email");
                            logs.registrar("WARN", "LOGIN_FALHOU", "Tentativa de autenticação não concluída.", identificador);
                            response.sendRedirect(request.getContextPath() + "/login?erro");
                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            if (authentication != null) {
                                logs.registrar("INFO", "LOGOUT", "Sessão encerrada pelo usuário.", authentication.getName());
                            }
                            response.sendRedirect(request.getContextPath() + "/login?saiu");
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("SESSION")
                        .permitAll())
                .exceptionHandling(exceptions -> exceptions.accessDeniedPage("/acesso-negado"))
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            MongoUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}

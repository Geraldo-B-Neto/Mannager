package com.devWebResolv.mannagerSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Configuração da cadeia de filtros de segurança (SecurityFilterChain)
     * Utiliza a abordagem moderna (Spring Security 6+ / Spring Boot 3+), baseada em métodos lambda e beans.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 1. Liberação de arquivos estáticos (CSS, JS, Imagens, Webjars, Favicon)
                .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/webjars/**",
                    "/favicon.ico"
                ).permitAll()

                // 2. Rotas públicas da aplicação (página inicial, login, etc.)
                .requestMatchers("/", "/home", "/login", "/register", "/public/**").permitAll()

                // 3. Rotas com controle de acesso baseado em Roles
                // No Spring Security, hasRole("ADMIN") valida a authority "ROLE_ADMIN"
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // Rotas acessíveis tanto por ADMIN quanto por USER
                .requestMatchers("/user/**", "/dashboard/**").hasAnyRole("USER", "ADMIN")

                // 4. Qualquer outra requisição requer autenticação prévia
                .anyRequest().authenticated()
            )
            // Configuração do formulário de login (Form Login)
            .formLogin(form -> form
                .loginPage("/login")                      // Rota da página personalizada de login
                .defaultSuccessUrl("/dashboard", true)    // Redirecionamento após login bem-sucedido
                .failureUrl("/login?error=true")          // Redirecionamento em caso de falha
                .permitAll()                              // Permite acesso irrestrito ao endpoint de login
            )
            // Configuração de Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            // Página amigável para acesso negado (403 Forbidden)
            .exceptionHandling(handling -> handling
                .accessDeniedPage("/access-denied")
            );

        return http.build();
    }

    /**
     * Bean para codificação de senhas utilizando o algoritmo BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuração de usuários em memória para testes e validação imediata.
     * Obs.: Pode ser substituído posteriormente por um UserDetailsService customizado integrado ao banco de dados JPA.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN") // Adiciona automaticamente o prefixo ROLE_ -> ROLE_ADMIN
                .build();

        UserDetails regularUser = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")  // Adiciona automaticamente o prefixo ROLE_ -> ROLE_USER
                .build();

        return new InMemoryUserDetailsManager(adminUser, regularUser);
    }
}

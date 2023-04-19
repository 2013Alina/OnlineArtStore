package com.example.onlineartstore.config;

import com.zaxxer.hikari.util.DriverDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


//@EnableWebSecurity
//@EnableWebFluxSecurity
@Configuration
public class WebSecurityConfig {


//    matcher - сопоставитель, соответствие правилам, путям, паттернам

//    @Bean("h2DataSource")
//    public DataSource getH2DataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScripts("schema.sql") //если убрать эту строку, то схема будет сама сгенерирована
//                .build();
//    }

//    @Bean
//    public DataSource getDataSource() {
//        Properties properties = new Properties();
//        DriverDataSource sa = new DriverDataSource("jdbc:h2:file:~/testdb", "org.h2.Driver", properties, "sa", "");
//        return sa;
//    }


    // РАБОТАЕТ!

    @Bean
    public UserDetailsManager getUserDetailsService(DataSource dataSource) {
        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$QgcqI.FMy8Z73QH1Fk/Eqeix.oBu41ejFnDwSOrgtG4IHG9fInHIm") //хэшированный пароль admin
                .roles("ADMIN", "USER")
                .build();
        UserDetails user = User.builder()
                .username("user")
                .password("$2a$10$9zIdGinlpulUYzYPeqwZTO.mOD0uKOWiofG5Rj8kGazk.XGxTlEX6") //хэшированный пароль user
                .roles("USER")
                .build();
        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);//для хранения данных в базе Н2
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(); // кодирует пароль, есть защита!
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .mvcMatchers("/**", "/singup/**", "/images/**").permitAll()  //всем доступна главная страница и регистрация!!!
                        .mvcMatchers("/privatePage/**").authenticated() // mvcMatchers("/privatePage") здесь обязательная авторизация
                        .mvcMatchers("/mainAdminPage/**").hasRole("ADMIN") //только для роли ADMIN
                        .mvcMatchers("/adminPage/**").hasRole("ADMIN")
                        .mvcMatchers("/updateCategory/**").hasRole("ADMIN")
                        .mvcMatchers("/updateAuthor/**").hasRole("ADMIN")
                        .mvcMatchers("/updatePainting/**").hasRole("ADMIN")
                        .mvcMatchers("/adminPageBets/**").hasRole("ADMIN")
                        .mvcMatchers("/allBets/**").hasRole("ADMIN")
                        .mvcMatchers("/adminPageUsers/**").hasRole("ADMIN")
                        .mvcMatchers("/userPage/**", "/infoPage/**").hasRole("USER") //только для роли USER, но ADMIN тоже зайдет так как  UserDetails admin = User.builder()..roles("ADMIN", "USER")
                        .mvcMatchers("/participateAuction/**").hasRole("USER")
                        .mvcMatchers("/updateUserDetailClient/**").hasRole("USER")
                        .mvcMatchers("/victoryPage/**").hasRole("USER")
                        .anyRequest().denyAll()) //все остальные запросы запрещены!!!
                .formLogin()
                .and()
                //.csrf().disable() //отключить систему безопасности csrf()
                .build(); //mvcMatchers("/privatePage") здесь обязательная авторизация

    }
}

    // НЕ РАБОТАЕТ

//    @Bean
//    public ReactiveUserDetailsService DetailsService() {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("$2a$10$QgcqI.FMy8Z73QH1Fk/Eqeix.oBu41ejFnDwSOrgtG4IHG9fInHIm") // hashed password for admin
//                .roles("ADMIN", "USER")
//                .build();
//        UserDetails user = User.builder()
//                .username("user")
//                .password("$2a$10$9zIdGinlpulUYzYPeqwZTO.mOD0uKOWiofG5Rj8kGazk.XGxTlEX6") // hashed password for user
//                .roles("USER")
//                .build();
//        return new MapReactiveUserDetailsService(admin, user);
//    }
//
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder(); // кодирует пароль, есть защита!
//    }
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .authorizeExchange()
//                .pathMatchers("/", "/signup/**", "/images/**").permitAll()
//                .pathMatchers("/privatePage/**").authenticated()
//                .pathMatchers("/mainAdminPage/**").hasRole("ADMIN")
//                .pathMatchers("/adminPage/**", "/updateCategory/**", "/updateAuthor/**", "/updatePainting/**", "/adminPageBets/**", "/allBets/**", "/adminPageUsers/**").hasRole("ADMIN")
//                .pathMatchers("/userPage/**", "/infoPage/**").hasRole("USER")
//                .pathMatchers("/participateAuction/**", "/updateUserDetailClient/**", "/victoryPage/**").hasRole("USER")
//                .anyExchange().denyAll()
//                .and()
//                .formLogin()
//                .and()
//                .build();
//    }
//
//    @Bean
//    public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(DetailsService());
//        authenticationManager.setPasswordEncoder(passwordEncoder);
//        return authenticationManager;
//    }
//}

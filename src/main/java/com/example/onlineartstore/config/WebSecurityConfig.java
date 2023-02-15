package com.example.onlineartstore.config;

import com.zaxxer.hikari.util.DriverDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class WebSecurityConfig {


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
//        return new InMemoryUserDetailsManager(admin, user);//для хранения данных в памяти
        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);//для хранения данных в базе Н2
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
//        return NoOpPasswordEncoder.getInstance();//никак не кодирует данные, возвращает строку, не рекомендуется к использованию
        return new BCryptPasswordEncoder(); // кодирует пароль, есть защита!
    }

    //    Наш фильтр с доступами к страницам
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
//                .formLogin()
//                .and()
//                .build(); //authenticated() это настройки по умолчанию default, любой запрос будет аутентифицирован!!!каждый раз нужны login password!

//        return httpSecurity
//                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
//                .formLogin()
//                .and()
//                .build(); //  permitAll() - все запросы разрешены!!! login password не нужны!

        return httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .mvcMatchers("/**", "/singup/**").permitAll()  //всем доступна главная страница и регистрация!!!
                        .mvcMatchers("/privatePage/**").authenticated() // mvcMatchers("/privatePage") здесь обязательная авторизация
                        .mvcMatchers("/mainAdminPage/**").hasRole("ADMIN") //только для роли ADMIN
                        .mvcMatchers("/adminPage/**").hasRole("ADMIN")
                        .mvcMatchers("/updateCategory/**").hasRole("ADMIN")
                        .mvcMatchers("/updateAuthor/**").hasRole("ADMIN")
                        .mvcMatchers("/updatePainting/**").hasRole("ADMIN")
                        .mvcMatchers("/adminPageBets/**").hasRole("ADMIN")
                        .mvcMatchers("/allBets/**").hasRole("ADMIN")
                        .mvcMatchers("/adminPageUsers/**").hasRole("ADMIN")
                        .mvcMatchers("/userPage/**", "/infoPage/**", "/participateAuction/**").hasRole("USER") //только для роли USER, но ADMIN тоже зайдет так как  UserDetails admin = User.builder()..roles("ADMIN", "USER")
                        .anyRequest().denyAll()) //все остальные запросы запрещены!!!
                .formLogin()
                .and()
                //.csrf().disable() //отключить систему безопасности csrf()
                .build(); //mvcMatchers("/privatePage") здесь обязательная авторизация

    }

    //    matcher - сопоставитель, соответствие правилам, путям, паттернам


}

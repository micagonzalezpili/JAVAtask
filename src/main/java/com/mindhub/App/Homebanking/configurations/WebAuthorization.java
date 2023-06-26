package com.mindhub.App.Homebanking.configurations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@EnableWebSecurity // habilita web security para poder trabajar con la misma
@Configuration // configura la app antes de que inicie
public class WebAuthorization {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/web/sign-up.html").permitAll()
                .antMatchers("/style/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/logout").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers("/api/clients/current").hasAuthority("CLIENT")
                .antMatchers("/web/accounts.html").hasAnyAuthority("CLIENT", "ADMIN")  // Permite acceso a CLIENT y ADMIN
                .antMatchers("/web/account.html").hasAnyAuthority("CLIENT", "ADMIN")  // Permite acceso a CLIENT y ADMIN
                .antMatchers("/web/cards.html").hasAnyAuthority("CLIENT", "ADMIN")  // Permite acceso a CLIENT y ADMIN
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/web/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console").hasAuthority("ADMIN")
                .anyRequest().denyAll();


        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login"); // defino la ruta donde realizaremos el login

        http.logout().logoutUrl("/api/logout");

        // Desactivar verificación de tokens CSRF
        http.csrf().disable();

        // Deshabilitar frameOptions para acceder a h2-console
        http.headers().frameOptions().disable();

        // Si el usuario no está autenticado, simplemente enviar una respuesta de error de autenticación
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Si el inicio de sesión es exitoso, simplemente borrar las banderas que solicitan autenticación (osea el mail y el pwd, se pone en falso mientras la sesion este activa)
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // Si el inicio de sesión falla, simplemente enviar una respuesta de error de autenticación
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Si el cierre de sesión es exitoso, simplemente enviar una respuesta exitosa
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}


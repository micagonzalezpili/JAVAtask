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

@EnableWebSecurity
@Configuration
public class WebAuthorization {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/api/login","/index.html", "/web/sign-up.html","/web/sign-in.html").permitAll() // ACOMODAR EN MENOS LINEAS
                .antMatchers("/style/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/web/contact.html").permitAll()
                .antMatchers("/web/text.html").permitAll()
                .antMatchers(HttpMethod.POST, "/api/logout").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers(HttpMethod.POST, "/api/transactions").hasAuthority("CLIENT")
                .antMatchers("/api/accounts/**").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers("/web/accounts.html").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers("/web/account.html").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers("/web/cards.html").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers("/web/create-cards.html").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers("/web/transfers.html").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/web/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console/**").hasAuthority("ADMIN")
                .anyRequest().denyAll();

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        http.csrf().disable(); // las csrf mandan solicitudes como si fueran el usuario autenticado

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


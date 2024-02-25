package com.tienda5.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de los filtros de seguridad
 * Spring Security hace la autenticación y la autorización
 */
@Configuration
@EnableMethodSecurity
public class Seguridad {
	
	@Autowired
	private UsuarioDetalles usuDetalle;

	
	/**
     * Codificador de contraseñas
     * @return instancia
     */
    @Bean
    BCryptPasswordEncoder encriptarClave() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configuración del proveedor de autenticación
	 * Utiliza un UsuarioDetalles personalizado para cargar los detalles del usuario desde la base de datos
	 * @return
	 */
    @Bean
    DaoAuthenticationProvider autenticacion() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(usuDetalle);
	    authProvider.setPasswordEncoder(encriptarClave());//Cifrar y comparar las contraseñas
	    return authProvider;
	}
	
    /**
     * Filtros de seguridad de spring security para solicitudes HTTP
     * @param http configuracion de seguridad
     * @return filtros de seguridad
     * @throws Exception lanza excepción
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	//Configura:
    	
    	//las reglas de autorización para las peticiones HTTP
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/inicioSesion","/registro","/confirmarCuenta","/clave/**","/js/**").permitAll()
        										.anyRequest().authenticated()//Pide autenticación para cualquier otra petición HTTP
            )
            //Inicio de sesión y la página de inicio de sesión
            .formLogin(login -> login.loginPage("/inicioSesion")//Página de inicio de sesión personalizada
                     				 .defaultSuccessUrl("/principal", true)//Redirección de inicio de sesión exitoso
                     				 .loginProcessingUrl("/procesarInicioSesion")//Establece la URL de procesamiento para el formulario de inicio de sesión
            )
            //Cierre de sesión
            .logout(logout -> logout.logoutUrl("/cerrarSesion")//URL de cierre de sesión personalizada
                    				.logoutSuccessUrl("/inicioSesion")//Redirección después de un cerrar de sesión
            )
            ;

        
        //Autenticación personalizado
        http.authenticationProvider(autenticacion());

        //Devuelve la cadena de filtros de seguridad
        return http.build();
    }

	//falta administrador
    /**
     * Configuración del admnistrador
     * @param authConfig
     * @return
     * @throws Exception
     */
    @Bean
    AuthenticationManager authenticationAdmin(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	}
    
}

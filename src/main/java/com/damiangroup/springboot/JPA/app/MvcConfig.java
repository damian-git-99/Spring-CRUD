package com.damiangroup.springboot.JPA.app;

import java.nio.file.Paths;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	/*
	 * registrar la ruta de las imagenes en spring
	 * */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		//Registrar otro directorio como un recurso estatico, aparte del static que ya viene registrado por default
		//En Spring, y lo mapeamos a una ruta (/uploads/). Le indicamos al contexto de spring
		String resorcePathString = Paths.get("uploads").toAbsolutePath().toUri().toString();
		registry.addResourceHandler("/uploads/**")//.addResourceLocations(resorcePathString); 
			.addResourceLocations(resorcePathString);
			//.addResourceLocations("file:uploads/"); // Ruta desde la raiz del proyecto (relativa)
			//.addResourceLocations("file:C:/Users/Damian/Documents/workspace-spring-tool-suite-4-4.7.1.RELEASE/spring-boot-JPA/uploads/"); // Ruta absoluta
			//addResourceHandler = nombre del recurso
			//addResourceLocations = ruta de donde se va a guardar ese recurso
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	
	@Bean
	public LocaleResolver localeResolver() {
		// se encarga de guardar el objeto locale con el idioma, y lo guarda en un resolver, en la sesion HTTP
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es", "ES"));
		return localeResolver;
	}
	
	@Bean
	public LocaleChangeInterceptor	localeChangeInterceptor() {
		// cada que se pase por url atravez de get lang se ejecuta este interceptor
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	//Metodo para registrar el interceptor localeChangeInterceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	
}

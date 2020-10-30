package com.damiangroup.springboot.JPA.app;

import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

	
}

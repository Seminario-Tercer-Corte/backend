package com.seminario.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*
import javax.servlet.Filter

@SpringBootApplication
@EnableSwagger2
class ApiApplication {

	/**
	 * Enable cors
	 * @return FilterRegistrationBean
	 */
	@Bean
	fun simpleCorsFilter(): FilterRegistrationBean<*> {
		val source = UrlBasedCorsConfigurationSource()
		val config = CorsConfiguration()
		config.allowCredentials = true
		config.allowedOrigins = Collections.singletonList("*")
		config.allowedMethods = Collections.singletonList("*")
		config.allowedHeaders = Collections.singletonList("*")
		source.registerCorsConfiguration("/**", config)
		val bean = FilterRegistrationBean<Filter>(CorsFilter(source))
		bean.order = Ordered.HIGHEST_PRECEDENCE
		return bean
	}
}

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}
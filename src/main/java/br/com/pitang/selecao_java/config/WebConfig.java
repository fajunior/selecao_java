package br.com.pitang.selecao_java.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

@Configuration
@EnableWebMvc
public class WebConfig implements Filter, WebMvcConfigurer {
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToLocalDateTimeConverter());
	}

	static class StringToLocalDateTimeConverter implements Converter<String, LocalDate> {
		@Override
		public LocalDate convert(String text) {
			LocalDate date = LocalDate.now();
			date = LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			return date;
		}
	}

	/*
	 * public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**") // Mapeia todos os endpoints
	 * .allowedOrigins("http://localhost:4200") // Permite o domínio do frontend
	 * .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite os
	 * métodos HTTP necessários .allowedHeaders("*") // Permite todos os cabeçalhos
	 * .allowCredentials(true); // Permite credenciais (opcional, depende do seu
	 * caso de uso) }
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		

		for (Enumeration<?> e = request.getParameterNames(); e.hasMoreElements();) {
			String nextHeaderName = (String) e.nextElement();
			String headerValue = request.getParameter(nextHeaderName);
			System.out.println(nextHeaderName+": "+ headerValue);
		}
		
		for (Enumeration<?> e = req.getParameterNames(); e.hasMoreElements();) {
			String nextHeaderName = (String) e.nextElement();
			String headerValue = req.getParameter(nextHeaderName);
			System.out.println(nextHeaderName+": "+ headerValue);
		}

		System.out.println("WebConfig; " + request.getRequestURI());
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Content-Encoding", "gzip, deflate, br");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Expose-Headers", "Authorization");
		response.addHeader("Access-Control-Expose-Headers", "responseType");
		response.addHeader("Access-Control-Expose-Headers", "observe");
		System.out.println("Request Method: " + request.getMethod());
		if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
			try {
				chain.doFilter(req, res);
			} catch (ConstraintViolationException e){
				e.printStackTrace();
				HttpServletResponse httpResponse = (HttpServletResponse) res;
				httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				
				System.out.println(e.getConstraintViolations());
				
			    
			    // Optionally, write an error message to the response body
			    PrintWriter writer = httpResponse.getWriter();
			    writer.write("An error occurred. There is mandatory fiels without value!");
			    writer.close();
			} catch (Exception e) {
				 Throwable cause = e;
				    while (cause!= null) {
				        System.out.println("Cause: " + cause);
				        cause = cause.getCause();
				    }
				e.printStackTrace();
				HttpServletResponse httpResponse = (HttpServletResponse) res;
				httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			    
			    // Optionally, write an error message to the response body
			    PrintWriter writer = httpResponse.getWriter();
			    writer.write("An error occurred.");
			    writer.close();
			    
			    //throw e;
			}
		} else {
			System.out.println("Pre-flight");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Content-Encoding", "gzip, deflate, br");
			response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "Access-Control-Expose-Headers"
					+ "Authorization, content-type," + "USERID" + "ROLE"
					+ "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with,responseType,observe");
			response.setStatus(HttpServletResponse.SC_OK);
		}

	}
}

package task.garage.controller;

import java.lang.reflect.Method;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;


import task.garage.error.RestErrorHandler;
import task.garage.model.ParkingService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@RunWith(MockitoJUnitRunner.class)
public class GarageControllerTest {

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8")
			);

	private static final String DESCRIPTION = "description";
	private static final String ID = "id";
	private static final String TITLE = "title";

	private static final int MAX_LENGTH_DESCRIPTION = 500;
	private static final int MAX_LENGTH_TITLE = 100;

	@Mock
	private ParkingService service;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new GarageController(service))
				.setHandlerExceptionResolvers(withExceptionControllerAdvice())
				.build();
	}

	private ExceptionHandlerExceptionResolver withExceptionControllerAdvice() {
		final ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
			@Override
			protected ServletInvocableHandlerMethod getExceptionHandlerMethod(final HandlerMethod handlerMethod,
					final Exception exception) {
				Method method = new ExceptionHandlerMethodResolver(RestErrorHandler.class).resolveMethod(exception);
				if (method != null) {
					ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
					messageSource.setBasename("messages");
					return new ServletInvocableHandlerMethod(new RestErrorHandler(messageSource), method);
				}
				return super.getExceptionHandlerMethod(handlerMethod, exception);
			}
		};
		exceptionResolver.afterPropertiesSet();
		return exceptionResolver;
	}
	@Test
	public void createParkingSpaces1() throws Exception {
		mockMvc.perform(get("/garage/equalParkingSpaces?levels=2&mSpaces=10&cSpaces=5")
		.accept(MediaType.APPLICATION_JSON)) 
		.andExpect(status().isOk())
		.andDo(print());
	}
//	@Test
//	public void createParkingSpaces() throws Exception {
//
//		mockMvc.perform(get("/garage/equalParkingSpaces?levels=2&mSpaces=10&cSpaces=5"))
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.total", hasValue(30)));
//	}
}
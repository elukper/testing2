package com.testing.two.config.webconfig;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.testing.two.application.controller"})
public class ServletConfig extends WebMvcConfigurerAdapter {

    //region Config for model and view
    /**
     * Creates direct mapping between the index.html URL and mapping
     * @param registry {@link ViewControllerRegistry} to which the mapping is registered to
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index");
        registry.addRedirectViewController("/","/index");
        registry.addViewController("/login");
    }

    /**
     * View Resolver bean for .html files
     * @return new {@link ViewResolver} bean singleton
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    /**
     * Configure static content handling
     * @param defaultServletHandlerConfigurer DefaultServletHandlerConfigurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {
        defaultServletHandlerConfigurer.enable();
    }
    //endregion

    //region Config for Message Converters (JSON, XML...)
    /**
     * Add a custom MappingJackson2HttpMessageConverter to override the default one
     * This one will have JsonInclude.Include.NON_NULL configured, so null values are not returned (otherwise this has to be defined in DAO class)
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(createJackson2HttpMessageConverter());
    }

    /**
     * Creates a new Message Converter for JSON
     * Use with override method configureMessageConverters
     * @return
     */
    private MappingJackson2HttpMessageConverter createJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    /**
     * Create a new Message Converter for XML
     * User with override method configureMessageConverters
     * @return
     */
    private HttpMessageConverter<Object> createXmlHttpMessageConverter() {
        MarshallingHttpMessageConverter xmlConverter =
                new MarshallingHttpMessageConverter();

        XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
        xmlConverter.setMarshaller(xstreamMarshaller);
        xmlConverter.setUnmarshaller(xstreamMarshaller);

        return xmlConverter;
    }
    //endregion
}

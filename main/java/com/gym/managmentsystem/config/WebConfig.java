package com.gym.managmentsystem.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer{


    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/login/postlogin").setViewName("trainerShow");
        registry.addViewController("/login/postlogin").setViewName("clientShow");
        //registry.addViewController("/home").setViewName("userhome");
        registry.addViewController("/trainer/getTrainerPage").setViewName("trainerShow");
        registry.addViewController("/client/getClientPage").setViewName("clientShow");
        registry.addViewController("/management/postClientUpdate/{client_id}").setViewName("managementClientUpdate");
        registry.addViewController("/management/getClientInfoCreate/{client_id}").setViewName("managementClientCreateInfo");
//        registry.addViewController("/403").setViewName("403");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(stringHttpMessageConverter);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

}

package org.activiti.app.ui;

import org.activiti.app.conf.ApplicationConfiguration;
import org.activiti.app.servlet.ApiDispatcherServletConfiguration;
import org.activiti.app.servlet.AppDispatcherServletConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * @Auther: yaojc
 * @Date: 2019/7/9
 * @Description: 定义spring-boot启动类，将activiti-app模块改造成springBoot工程
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class/*,
        HibernateJpaAutoConfiguration.class*/})
@Import({ApplicationConfiguration.class})
public class ActivitiUiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiUiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(ActivitiUiApplication.class);
    }

    @Bean
    public ServletRegistrationBean apiDispatcherServlet(){
        DispatcherServlet api = new DispatcherServlet();
        api.setContextClass(AnnotationConfigWebApplicationContext.class);
        api.setContextConfigLocation(ApiDispatcherServletConfiguration.class.getName());

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(api);
        servletRegistrationBean.addUrlMappings("/api/*");
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.setAsyncSupported(true);
        servletRegistrationBean.setName("api");

        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean appDispatcherServlet () {
        DispatcherServlet app = new DispatcherServlet();
        app.setContextClass(AnnotationConfigWebApplicationContext.class);
        app.setContextConfigLocation(AppDispatcherServletConfiguration.class.getName());

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(app);
        servletRegistrationBean.addUrlMappings("/app/*");
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.setAsyncSupported(true);
        servletRegistrationBean.setName("app");

        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean openEntityManagerInViewFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new OpenEntityManagerInViewFilter());

        bean.addUrlPatterns("/*");
        bean.setName("openEntityManagerInViewFilter");
        bean.setOrder(-200);

        bean.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC));

        return bean;
    }

}

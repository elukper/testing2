package com.testing.two;

import com.testing.two.config.profiles.Profiles;
import com.testing.two.config.rootconfig.database.DataSourceConfig;
import com.testing.two.config.rootconfig.database.SpringDataJpaConfig;
import com.testing.two.config.rootconfig.RootConfig;
import com.testing.two.config.webconfig.ServletConfig;
import com.testing.two.config.webconfig.security.SecurityConfigInMemory;
import com.testing.two.config.webconfig.security.SecurityConfigRemoteDba;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ApplicationInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        //TODO implement selection via properties
        //Use either StandardHibernateConfig or SpringDataJpaConfig, not both. If both are to be used, make sure to configure separate datasources (preferably to separate databases)
        //Class[] rootConfigs = new Class[]{RootConfig.class, DataSourceConfig.class, SecurityConfig.class, StandardHibernateConfig.class};
        Class[] rootConfigs = new Class[]{RootConfig.class, DataSourceConfig.class, SecurityConfigInMemory.class, SecurityConfigRemoteDba.class, SpringDataJpaConfig.class};


        return rootConfigs;
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {ServletConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        //Default profile is set as "dev"
        //Running the config with a different profile will set "spring.properties.active" to that profile, which will override the default one
        servletContext.setInitParameter("spring.properties.default", Profiles.DEV);
    }
}

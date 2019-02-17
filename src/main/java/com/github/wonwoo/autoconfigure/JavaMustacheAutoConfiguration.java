package com.github.wonwoo.autoconfigure;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import com.github.wonwoo.EnvironmentReflectionObjectHandler;
import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mustache.MustacheProperties;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(MustacheFactory.class)
@EnableConfigurationProperties(MustacheProperties.class)
@Import({ JavaMustacheServletWebConfiguration.class, JavaMustacheReactiveWebConfiguration.class })
public class JavaMustacheAutoConfiguration {

    private static final Log logger = LogFactory.getLog(JavaMustacheAutoConfiguration.class);

    private final MustacheProperties mustache;
    private final ApplicationContext applicationContext;

    public JavaMustacheAutoConfiguration(MustacheProperties mustache, ApplicationContext applicationContext) {
        this.mustache = mustache;
        this.applicationContext = applicationContext;
    }

    @Bean
    @ConditionalOnMissingBean
    public MustacheFactory mustacheFactory() {
        DefaultMustacheFactory defaultMustacheFactory = new DefaultMustacheFactory();
        defaultMustacheFactory.setObjectHandler(environmentReflectionObjectHandler());
        return defaultMustacheFactory;
    }

    @Bean
    public EnvironmentReflectionObjectHandler environmentReflectionObjectHandler() {
        return new EnvironmentReflectionObjectHandler();
    }

    @PostConstruct
    public void checkTemplateLocationExists() {
        if (this.mustache.isCheckTemplateLocation()) {
            TemplateLocation location = new TemplateLocation(this.mustache.getPrefix());
            if (!location.exists(this.applicationContext)) {
                logger.warn("Cannot find template location: " + location
                    + " (please add some templates, check your Mustache "
                    + "configuration, or set spring.mustache."
                    + "check-template-location=false)");
            }
        }
    }
}

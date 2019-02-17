package com.github.wonwoo.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.mustachejava.MustacheFactory;
import com.github.wonwoo.EnvironmentReflectionObjectHandler;
import com.github.wonwoo.web.JavaMustacheViewResolver;
import org.junit.Test;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class JavaMustacheAutoConfigurationTests {

    private AnnotationConfigWebApplicationContext webContext;

    private AnnotationConfigReactiveWebApplicationContext reactiveWebContext;

    @Test
    public void registerBeansForServletApp() {
        loadWithServlet(JavaMustacheAutoConfiguration.class);
        assertThat(this.webContext.getBeansOfType(MustacheFactory.class)).hasSize(1);
        assertThat(this.webContext.getBeansOfType(EnvironmentReflectionObjectHandler.class))
            .hasSize(1);
        assertThat(this.webContext.getBeansOfType(JavaMustacheViewResolver.class)).hasSize(1);
    }


    @Test
    public void registerBeansForReactiveApp() {
        loadWithReactive(JavaMustacheAutoConfiguration.class);
        assertThat(this.reactiveWebContext.getBeansOfType(MustacheFactory.class)).hasSize(1);
        assertThat(this.reactiveWebContext.getBeansOfType(EnvironmentReflectionObjectHandler.class))
            .hasSize(1);
        assertThat(this.reactiveWebContext.getBeansOfType(com.github.wonwoo.reactive.JavaMustacheViewResolver.class)).hasSize(1);
    }

    private void loadWithServlet(Class<?> config) {
        this.webContext = new AnnotationConfigWebApplicationContext();
        TestPropertyValues.of("spring.mustache.prefix=classpath:/templates/")
            .applyTo(this.webContext);
        if (config != null) {
            this.webContext.register(config);
        }
        this.webContext.refresh();
    }

    private void loadWithReactive(Class<?> config) {
        this.reactiveWebContext = new AnnotationConfigReactiveWebApplicationContext();
        TestPropertyValues.of("spring.mustache.prefix=classpath:/templates/")
            .applyTo(this.reactiveWebContext);
        if (config != null) {
            this.reactiveWebContext.register(config);
        }
        this.reactiveWebContext.refresh();
    }

}
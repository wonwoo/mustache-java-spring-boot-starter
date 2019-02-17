package com.github.wonwoo.reactive;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;

public class JavaMustacheViewResolverTests {

    private JavaMustacheViewResolver resolver = new JavaMustacheViewResolver();

    @Before
    public void init() {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.refresh();
        this.resolver.setApplicationContext(applicationContext);
        this.resolver.setPrefix("classpath:/templates/");
        this.resolver.setSuffix(".mustache");
    }

    @Test
    public void resolveNonExistent() {
        assertThat(
            this.resolver.resolveViewName("bar", null).block(Duration.ofSeconds(30)))
            .isNull();
    }

    @Test
    public void resolveExisting() {
        assertThat(this.resolver.resolveViewName("template", null)
            .block(Duration.ofSeconds(30))).isNotNull();
    }
}
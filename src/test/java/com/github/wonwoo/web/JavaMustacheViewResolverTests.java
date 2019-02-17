package com.github.wonwoo.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.View;

public class JavaMustacheViewResolverTests {

    private JavaMustacheViewResolver resolver = new JavaMustacheViewResolver();

    @Before
    public void init() {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.refresh();
        resolver.setApplicationContext(applicationContext);
        resolver.setServletContext(new MockServletContext());
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".mustache");
    }

    @Test
    public void resolveNonExistent() throws Exception {
        assertThat(resolver.resolveViewName("bar", null))
            .isNull();
    }

    @Test
    public void resolveNullLocale() throws Exception {
        View foo = resolver.resolveViewName("foo", null);
        assertThat(foo)
            .isNotNull();
    }
}
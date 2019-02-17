package com.github.wonwoo.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.mustachejava.DefaultMustacheFactory;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class JavaMustacheViewTests {

    private MockHttpServletRequest request = new MockHttpServletRequest();

    private MockHttpServletResponse response = new MockHttpServletResponse();

    private AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

    @Before
    public void init() {
        this.context.refresh();
        MockServletContext servletContext = new MockServletContext();
        this.context.setServletContext(servletContext);
        servletContext.setAttribute(
            WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
            this.context);
    }

    @Test
    public void viewResolvesHandlebars() throws Exception {
        JavaMustacheView view = new JavaMustacheView();
        view.setMustacheFactory(new DefaultMustacheFactory());
        view.setUrl("classpath:/templates/template.mustache");
        view.setApplicationContext(this.context);
        view.render(Collections.singletonMap("World", "Spring"), this.request,
            this.response);
        assertThat(this.response.getContentAsString()).isEqualTo("Hello Spring");
    }
}
package com.github.wonwoo.reactive;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.mustachejava.DefaultMustacheFactory;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;

public class JavaMustacheViewTests {

    private GenericApplicationContext context = new GenericApplicationContext();

    private MockServerWebExchange exchange;

    @Before
    public void init() {
        this.context.refresh();
    }

    @Test
    public void viewResolvesHandlebars() {
        this.exchange = MockServerWebExchange
            .from(MockServerHttpRequest.get("/test").build());
        JavaMustacheView view = new JavaMustacheView();
        view.setMustacheFactory(new DefaultMustacheFactory());
        view.setUrl("classpath:/templates/template.mustache");
        view.setCharset(StandardCharsets.UTF_8.displayName());
        view.setApplicationContext(this.context);
        view.render(Collections.singletonMap("World", "Spring"), MediaType.TEXT_HTML,
            this.exchange).block(Duration.ofSeconds(30));
        assertThat(this.exchange.getResponse().getBodyAsString()
            .block(Duration.ofSeconds(30))).isEqualTo("Hello Spring");
    }
}
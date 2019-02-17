package com.github.wonwoo.autoconfigure;

import com.github.mustachejava.MustacheFactory;
import com.github.wonwoo.reactive.JavaMustacheViewResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.mustache.MustacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnWebApplication(type = Type.REACTIVE)
class JavaMustacheReactiveWebConfiguration {

    private final MustacheProperties mustache;

    JavaMustacheReactiveWebConfiguration(MustacheProperties mustache) {
        this.mustache = mustache;
    }

    @Bean
    @ConditionalOnMissingBean
    public JavaMustacheViewResolver javaMustacheViewResolver(MustacheFactory mustacheFactory) {
        JavaMustacheViewResolver resolver = new JavaMustacheViewResolver(mustacheFactory);
        resolver.setPrefix(this.mustache.getPrefix());
        resolver.setSuffix(this.mustache.getSuffix());
        resolver.setViewNames(this.mustache.getViewNames());
        resolver.setCharset(this.mustache.getCharsetName());
        resolver.setRequestContextAttribute(this.mustache.getRequestContextAttribute());
        resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
        return resolver;
    }
}

package com.github.wonwoo.autoconfigure;

import com.github.mustachejava.MustacheFactory;
import com.github.wonwoo.web.JavaMustacheViewResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.mustache.MustacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
class JavaMustacheServletWebConfiguration {

    private final MustacheProperties mustache;

    JavaMustacheServletWebConfiguration(MustacheProperties mustache) {
        this.mustache = mustache;
    }

    @Bean
    @ConditionalOnMissingBean
    public JavaMustacheViewResolver javaMustacheViewResolver(MustacheFactory mustacheFactory) {
        JavaMustacheViewResolver resolver = new JavaMustacheViewResolver(mustacheFactory);
        this.mustache.applyToMvcViewResolver(resolver);
        resolver.setCharset(this.mustache.getCharsetName());
        resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
        return resolver;
    }

}

package com.github.wonwoo.reactive;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class JavaMustacheView extends AbstractUrlBasedView {

    private MustacheFactory mustacheFactory;

    private String viewName;

    private String charset;

    @Override
    public boolean checkResourceExists(Locale locale) {
        return getResource().exists();
    }

    @Override
    protected Mono<Void> renderInternal(Map<String, Object> model, MediaType contentType, ServerWebExchange exchange) {
        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().allocateBuffer();
        try (Reader reader = getReader(getResource())) {
            Mustache template = this.mustacheFactory.compile(reader, this.viewName);
            Charset charset = getCharset(contentType).orElse(getDefaultCharset());
            try (Writer writer = new OutputStreamWriter(dataBuffer.asOutputStream(),
                charset)) {
                template.execute(writer, model);
            }
        } catch (Exception ex) {
            DataBufferUtils.release(dataBuffer);
            return Mono.error(ex);
        }
        return exchange.getResponse().writeWith(Flux.just(dataBuffer));
    }

    private Resource getResource() {
        return getApplicationContext().getResource(this.getUrl());
    }

    private Reader getReader(Resource resource) throws IOException {
        if (this.charset != null) {
            return new InputStreamReader(resource.getInputStream(), this.charset);
        }
        return new InputStreamReader(resource.getInputStream());
    }

    private Optional<Charset> getCharset(MediaType mediaType) {
        return Optional.ofNullable((mediaType != null) ? mediaType.getCharset() : null);
    }

    public void setMustacheFactory(MustacheFactory mustacheFactory) {
        this.mustacheFactory = mustacheFactory;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

}

package com.github.wonwoo.reactive;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;

public class JavaMustacheViewResolver extends UrlBasedViewResolver {

    private final MustacheFactory mustacheFactory;

    private String charset;

    public JavaMustacheViewResolver() {
        this(new DefaultMustacheFactory());
    }

    public JavaMustacheViewResolver(MustacheFactory mustacheFactory) {
        this.mustacheFactory = mustacheFactory;
        setViewClass(requiredViewClass());
    }

    @Override
    protected Class<?> requiredViewClass() {
        return JavaMustacheView.class;
    }

    @Override
    protected AbstractUrlBasedView createView(String viewName) {
        JavaMustacheView view = (JavaMustacheView) super.createView(viewName);
        view.setMustacheFactory(mustacheFactory);
        view.setCharset(this.charset);
        view.setViewName(viewName);
        return view;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}

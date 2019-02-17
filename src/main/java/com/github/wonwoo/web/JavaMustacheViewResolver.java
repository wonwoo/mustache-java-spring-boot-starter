package com.github.wonwoo.web;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class JavaMustacheViewResolver extends AbstractTemplateViewResolver {

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
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        JavaMustacheView view = (JavaMustacheView) super.buildView(viewName);
        view.setMustacheFactory(mustacheFactory);
        view.setCharset(this.charset);
        view.setViewName(viewName);
        return view;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}

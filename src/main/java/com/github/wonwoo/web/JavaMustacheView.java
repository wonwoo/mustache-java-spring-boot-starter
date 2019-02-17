package com.github.wonwoo.web;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.AbstractTemplateView;

public class JavaMustacheView extends AbstractTemplateView {

    private MustacheFactory mustacheFactory;

    private String viewName;

    private String charset;

    @Override
    public boolean checkResource(Locale locale) {
        return getResource().exists();
    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Mustache template = createTemplate();
        if (template != null) {
            template.execute(response.getWriter(), model);
        }
    }

    private Mustache createTemplate() throws IOException {
        try (Reader reader = getReader(getResource())) {
            return this.mustacheFactory.compile(reader, this.viewName);
        }
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

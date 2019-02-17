package com.github.wonwoo;

import com.github.mustachejava.reflect.MissingWrapper;
import com.github.mustachejava.reflect.ReflectionObjectHandler;
import com.github.mustachejava.util.GuardException;
import com.github.mustachejava.util.Wrapper;
import java.util.List;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

public class EnvironmentReflectionObjectHandler extends ReflectionObjectHandler implements EnvironmentAware {

    private ConfigurableEnvironment environment;

    @Override
    public Wrapper find(String name, List<Object> scopes) {
        Wrapper wrapper = super.find(name, scopes);
        if (!(wrapper instanceof MissingWrapper)) {
            return wrapper;
        }
        return new PropertyWrapper(name);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    private class PropertyWrapper implements Wrapper {

        private final String name;

        private PropertyWrapper(String name) {
            this.name = name;
        }

        @Override
        public Object call(List<Object> scopes) throws GuardException {
            return EnvironmentReflectionObjectHandler.this.environment.getProperty(name);
        }
    }
}

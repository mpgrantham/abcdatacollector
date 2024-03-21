package com.canalbrewing.abcdatacollector.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import java.util.List;

import static java.util.Objects.nonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String SLASH = "/";
    private static final String EMPTY = "";
    private static final String LOCATION = "classpath:/static/";
    private static final String INDEX = "/index.html";
    private static final String ASTERISKS = "/**";

    private static final String[] endpointPatterns = new String[] { EMPTY, SLASH, ASTERISKS };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(endpointPatterns)
                .addResourceLocations(LOCATION)
                .resourceChain(false)
                .addResolver(new PathResourceResolver() {
                    @Override
                    public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
                        Resource resource = super.resolveResource(request, requestPath, locations, chain);
                        if (nonNull(resource)) {
                            return resource;
                        }
                        return super.resolveResource(request, INDEX, locations, chain);
                    }
                });
    }
}

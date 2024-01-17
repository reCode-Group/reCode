package com.dev.reCode

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry

@Configuration
class StaticResourceConfiguration   {
    fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/img/**")
            .addResourceLocations("file:ext-resources/")
            .setCachePeriod(0)

        registry.addResourceHandler("/video/**")
            .addResourceLocations("file:ext-resources/")
            .setCachePeriod(0)

        registry.addResourceHandler("/codemirror/**")
            .addResourceLocations("file:ext-resources/")
            .setCachePeriod(0)

        registry.addResourceHandler("/js/**")
            .addResourceLocations("file:ext-resources/")
            .setCachePeriod(0)

    }
}
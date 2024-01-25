package com.dev.reCode

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry


@Configuration
class StaticResourceConfiguration {
    fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        FILE_PATH_PATTERNS.forEach { pattern ->
            registry.addResourceHandler(pattern)
                .addResourceLocations(RESOURCE_LOCATION)
                .setCachePeriod(CACHE_PERIOD)
        }
    }

    private companion object {
        const val CACHE_PERIOD = 0
        const val RESOURCE_LOCATION = "file:ext-resources/"
        val FILE_PATH_PATTERNS = listOf("/img/**", "/video/**", "/codemirror/**", "/js/**")
    }
}
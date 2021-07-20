package emgc.randomlunch.config;

import emgc.randomlunch.interceptor.UrlPathInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.thumbnail.path}")
    private String path;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UrlPathInterceptor());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .exposedHeaders("X-AUTH-TOKEN")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.DELETE.name()
                )
                .allowCredentials(true)
                .allowedOrigins("http://localhost:3000", "https://react-randomlunch.vercel.app");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**", "/docs/**")
                .addResourceLocations("file://"+path, "classpath:/static/docs/");
    }
}

package emgc.randomlunch.config;

import emgc.randomlunch.interceptor.UrlPathInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Value("${file.thumbnail.path}")
    private String path;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UrlPathInterceptor());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true) //JSESSIONID를 기반으로 인증
                .allowedMethods("*")//기본 허용 메소드는 post와 get
                .allowedOrigins("http://localhost:3000");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:///"+path);
    }
}

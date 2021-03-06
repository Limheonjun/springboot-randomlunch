package emgc.randomlunch.config;

import emgc.randomlunch.filter.ResponseFilter;
import emgc.randomlunch.interceptor.UrlPathInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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
                .allowCredentials(true).maxAge(3600) //JSESSIONID를 기반으로 인증
                .allowedMethods("*")//기본 허용 메소드는 post와 get
                //끝부분에 /붙이면 안됨
                .allowedOrigins("http://192.168.1.43:3000",  // 동일한 와이파이, 다른 기기에서 front서버 접속할 경우
                        "http://dlagjswns.asuscomm.com:3000", // 외부 인터넷, 다른 기기에서 front서버 접속할 경우
                        "http://dlagjswns.asuscomm.com:7647", // http접속 후 https로 리다이렉션하는 경우(작동 안됨)
                        "https://react-randomlunch.vercel.app",
                        "http://localhost:3000"); // 동일한 와이파이, 같은 데스크탑 내 front서버에서 접속할 경우
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:///"+path);
    }

//    // responsefilter 적용 테스트
//    @Bean
//    public FilterRegistrationBean setFilterRegistration() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new ResponseFilter());
//        // filterRegistrationBean.setUrlPatterns(Collections.singletonList("/filtered/*")); // list 를 받는 메소드
////        filterRegistrationBean.addUrlPatterns("/filtered/*"); // string 여러개를 가변인자로 받는 메소드
//        return filterRegistrationBean;
//    }
}

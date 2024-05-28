package roomescape.infrastructure;

import auth.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver(jwtUtils()));
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor(jwtUtils()))
                .addPathPatterns("/admin/**"); // /admin/** 경로에 대하여 인터셉터 추가
    }

    @Bean
    public LoginMemberArgumentResolver loginMemberArgumentResolver(JwtUtils jwtUtils) {
        return new LoginMemberArgumentResolver(jwtUtils);
    }

    @Bean
    public AdminInterceptor adminInterceptor(JwtUtils jwtUtils) {
        return new AdminInterceptor(jwtUtils);
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }
}

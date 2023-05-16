package moviebuddy;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.concurrent.TimeUnit;

// 빈 구성정보 (Configuration Metadata)
@Configuration
@PropertySource("/application.properties")
@ComponentScan(basePackages = {"moviebuddy"}) // 자동 클래스 탐지 기법
@Import({MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})
@EnableCaching // 정교한 설정은 CachingConfigurer 이용
public class MovieBuddyFactory implements CachingConfigurer {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("moviebuddy");

        return marshaller;
    }

    @Bean
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS));

        return cacheManager;
    }

    @Override
    public CacheManager cacheManager() {
        return caffeineCacheManager();
    }

    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(caffeineCacheManager());
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        // @EnableAsync 비동기처리 컴포넌트 등록 -> AsyncConfigurer
        // @EnableScheduling 주기적 스케줄링 -> SchedulingConfigurer

        return new SimpleCacheErrorHandler();
    }

    /* @EnableCaching(선언적 캐싱 기법)으로 변경
    @Bean
    public CachingAspect cachingAspect(CacheManager cacheManager) {
        return new CachingAspect(cacheManager);
    }

    // 스프링 AOP API ProxyCreater, Advisor
    @Bean // 자동 프락시 생성기
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean // 캐싱 부가기능 제공 어드바이저
    public Advisor cachingAdvisor(CacheManager cacheManager) {
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, CacheResult.class);
        Advice advice = new CachingAdvice(cacheManager);

        // Advisor = PointCut(대상 선정 알고리즘) + Advice(부가기능)
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
    */

    @Configuration
    static class DomainModuleConfig {

    }

    @Configuration
    static class DataSourceModuleConfig {

    }

}

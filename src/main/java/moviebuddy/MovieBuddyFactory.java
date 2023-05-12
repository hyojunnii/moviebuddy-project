package moviebuddy;

import com.github.benmanes.caffeine.cache.Caffeine;
import moviebuddy.data.CachingMovieReader;
import moviebuddy.domain.MovieReader;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.concurrent.TimeUnit;

// 빈 구성정보 (Configuration Metadata)
@Configuration
@PropertySource("/application.properties")
@ComponentScan(basePackages = {"moviebuddy"}) // 자동 클래스 탐지 기법
@Import({MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})
public class MovieBuddyFactory {

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

    @Configuration
    static class DomainModuleConfig {

    }

    @Configuration
    static class DataSourceModuleConfig {

        @Primary
        @Bean
        public MovieReader cachingMovieReader(CacheManager cacheManager, MovieReader target) {
            return new CachingMovieReader(cacheManager, target);
        }
    }

}

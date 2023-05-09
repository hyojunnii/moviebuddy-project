package moviebuddy;

import moviebuddy.domain.CsvMovieReader;
import moviebuddy.domain.MovieFinder;
import moviebuddy.domain.MovieReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 빈 구성정보 (Configuration Metadata)
@Configuration
public class MovieBuddyFactory {

    @Bean
    public MovieReader movieReader() {
        return new CsvMovieReader();
    }

    /**
     * 자바 코드로 의존관계 주입하는 방법
     * 1. 메소드 콜 - movieReader() 직접 호출
     * 2. 메소드 파라미터
     * 3. 생성자
     * 4. 애노테이션
     */
    @Bean
    //@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) - 빈 스코프 미지정시 싱글톤
    public MovieFinder movieFinder(MovieReader movieReader) {
        return new MovieFinder(movieReader);
    }

}

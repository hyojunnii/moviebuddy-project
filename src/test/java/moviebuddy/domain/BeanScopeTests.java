package moviebuddy.domain;

import moviebuddy.MovieBuddyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScopeTests {

    @Disabled
    @DisplayName("빈 스코프 테스트 - 싱글톤(단 하나의 객체) / 프로토타입(매번 객체 생성)")
    @Test
    void Equals_MovieFinderBean() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MovieBuddyFactory.class);
        MovieFinder movieFinder = applicationContext.getBean(MovieFinder.class);

        Assertions.assertEquals(movieFinder, applicationContext.getBean(MovieFinder.class));
    }

}

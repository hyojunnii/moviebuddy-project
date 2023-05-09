package moviebuddy;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// 빈 구성정보 (Configuration Metadata)
@Configuration
@ComponentScan(basePackages = {"moviebuddy"}) // 자동 클래스 탐지 기법
@Import({MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})
//@ImportResource("xml file location")
public class MovieBuddyFactory {

    /**
     * 자바 코드로 의존관계 주입하는 방법
     * 1. 메소드 콜 - movieReader() 직접 호출
     * 2. 메소드 파라미터
     * 3. 생성자
     * 4. 애노테이션
     */

    @Configuration
    static class DomainModuleConfig {

    }

    @Configuration
    static class DataSourceModuleConfig {

    }

}

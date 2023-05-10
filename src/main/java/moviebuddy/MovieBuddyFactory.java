package moviebuddy;

import moviebuddy.data.CsvMovieReader;
import org.springframework.context.annotation.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

// 빈 구성정보 (Configuration Metadata)
@Configuration
@ComponentScan(basePackages = {"moviebuddy"}) // 자동 클래스 탐지 기법
@Import({MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})
public class MovieBuddyFactory {

   @Bean
   public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("moviebuddy");

        return marshaller;
    }

    @Configuration
    static class DomainModuleConfig {

    }

    @Configuration
    static class DataSourceModuleConfig {

       @Profile(MovieBuddyProfile.CSV_MODE)
       @Bean
       public CsvMovieReader csvMovieReader() {
           CsvMovieReader movieReader = new CsvMovieReader();
           movieReader.setMetadata("movie_metadata.csv");

           return movieReader;
       }

    }

}

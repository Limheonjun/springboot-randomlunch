package emgc.randomlunch;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class RandomlunchApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
        + "classpath:application.yml,"
        + "classpath:aws.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(RandomlunchApplication.class)
            .properties(APPLICATION_LOCATIONS)
            .run(args);
    }

}

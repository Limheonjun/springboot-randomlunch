package emgc.randomlunch.config;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class GeoIp2Config {

    @Bean
    public DatabaseReader databaseReader() throws IOException, GeoIp2Exception{
        File resource = new File("src/main/resources/GeoLite2-Country.mmdb");
        return new DatabaseReader.Builder(resource).build();
    }
}

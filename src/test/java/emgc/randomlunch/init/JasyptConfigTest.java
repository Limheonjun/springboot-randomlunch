package emgc.randomlunch.init;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JasyptConfigTest {

    @Qualifier("jasyptStringEncryptor")
    @Autowired
    StringEncryptor stringEncrytor;

    @Test
    void test(){
        assertNotNull(stringEncrytor);
        String text = "test";
        String url = stringEncrytor.encrypt("${spring.datasource.url}");
        String username = stringEncrytor.encrypt("${spring.datasource.username}");
        String password = stringEncrytor.encrypt("${spring.datasource.password}");
        String driver = stringEncrytor.encrypt("${spring.datasource.driver-class-name}");
        String port = stringEncrytor.encrypt("${server.port}");
        String path = stringEncrytor.encrypt("${file.thumbnail.path}");
        System.out.println("");

    }

}
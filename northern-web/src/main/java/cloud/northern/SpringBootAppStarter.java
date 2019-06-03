package cloud.northern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Boot
 *
 * @author SoulP
 *
 */
@ServletComponentScan
@SpringBootApplication
public class SpringBootAppStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAppStarter.class, args);
    }

}

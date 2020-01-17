package dj.aurelia.aureliauser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@EnableDiscoveryClient
public class AureliaUserApplication {


    public static Logger logger = LoggerFactory.getLogger(AureliaUserApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(AureliaUserApplication.class, args);
    }

}

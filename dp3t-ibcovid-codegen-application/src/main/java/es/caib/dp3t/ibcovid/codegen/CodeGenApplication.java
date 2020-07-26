package es.caib.dp3t.ibcovid.codegen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CodeGenApplication {

    public static void main(final String[] args) {
        SpringApplication.run(CodeGenApplication.class);
    }

}

package ne.ecole.schoolmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SchoolMgmtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolMgmtApplication.class, args);
    }
}


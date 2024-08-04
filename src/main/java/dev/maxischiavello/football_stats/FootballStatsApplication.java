package dev.maxischiavello.football_stats;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;

import static java.lang.StringTemplate.STR;

@SpringBootApplication
public class FootballStatsApplication {

    public static void main(String[] args) {

        SpringApplication.run(FootballStatsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(JdbcConnectionDetails jdbc) {
        return args -> {
            var details = STR. """
     				class: \{ jdbc.getClass().getName() }
     				JDBC URL: \{ jdbc.getJdbcUrl() }
     				Username: \{ jdbc.getUsername() }
     				Password: \{ jdbc.getPassword() }
					""" ;

            System.out.println(details);
        };
    }

}

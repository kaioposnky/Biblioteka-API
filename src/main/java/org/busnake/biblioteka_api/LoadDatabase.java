package org.busnake.biblioteka_api;

import org.busnake.biblioteka_api.Repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    // O que será executado após a inicialização da Database
    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository) {
        return args -> {
            log.info("Database loaded :]");
        };
    }
}

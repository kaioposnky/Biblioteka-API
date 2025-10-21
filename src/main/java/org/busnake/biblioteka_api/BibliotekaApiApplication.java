package org.busnake.biblioteka_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotekaApiApplication {

    private static final Logger log = LoggerFactory.getLogger(BibliotekaApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BibliotekaApiApplication.class, args);
        log.info("Biblioteka API is running at http://localhost:8080!");
    }

}

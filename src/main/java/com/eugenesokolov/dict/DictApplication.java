package com.eugenesokolov.dict;

import com.eugenesokolov.dict.model.TranslationWord;
import com.eugenesokolov.dict.repo.TranslationWordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class DictApplication {

    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(TranslationWordRepository repository) {
        return args -> {
            if (repository.findFirstBySourceAndSourceLang("hello1", "English") == null) {
                System.out.println("A new word found, saving");
                repository.save(new TranslationWord("hello1", "hallo2", "English", "German"));
            }

            repository.findAll().forEach(System.out::println);
        };
    }
}

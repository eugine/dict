package com.eugenesokolov.dict;

import com.eugenesokolov.dict.service.SpreadSheetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DictApplication {

    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoSpreadSheet(SpreadSheetService spreadSheetService) {
        return args -> spreadSheetService
                .fromSpreadSheet("1kUKnDEVsbbWzH3dMkkbErWipF3L9w90xXGQiCpPGJe0")
                .subscribe(System.out::println, System.err::println);
    }

    /*
    @Bean
    public CommandLineRunner demoMongo(TranslationWordRepository repository) {
        return args -> {
            if (repository.findFirstBySourceAndSourceLang("hello1", "English") == null) {
                System.out.println("A new word found, saving");
                repository.save(new TranslationWord("hello1", "hallo2", "English", "German"));
            }
            repository.findAll().forEach(System.out::println);
        };
    }
*/
}

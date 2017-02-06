package com.eugenesokolov.dict.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class TranslationWord {
    @Id
    private String id;

    private String source;
    private String translation;
    private String sourceLang;
    private String translationLang;

    public TranslationWord(String source, String translation, String sourceLang, String translationLang) {
        this.source = source;
        this.translation = translation;
        this.sourceLang = sourceLang;
        this.translationLang = translationLang;
    }
}

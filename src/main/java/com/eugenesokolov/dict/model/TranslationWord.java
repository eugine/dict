package com.eugenesokolov.dict.model;

import lombok.Data;

@Data
public class TranslationWord {
    private String source;
    private String translation;
    private String sourceLang;
    private String translationLang;
}

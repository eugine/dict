package com.eugenesokolov.dict.model;

import lombok.Value;

@Value
public class TranslationWord {
    private String source;
    private String translation;
    private String sourceLang;
    private String translationLang;
}

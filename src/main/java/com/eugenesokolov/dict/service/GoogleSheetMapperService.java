package com.eugenesokolov.dict.service;

import com.eugenesokolov.dict.model.TranslationWord;

import java.util.List;

public interface GoogleSheetMapperService {

    List<TranslationWord> mapResults(List<List<Object>> values);
}

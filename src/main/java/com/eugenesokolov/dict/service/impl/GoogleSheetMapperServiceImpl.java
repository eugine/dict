package com.eugenesokolov.dict.service.impl;

import com.eugenesokolov.dict.model.TranslationWord;
import com.eugenesokolov.dict.service.GoogleSheetMapperService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
class GoogleSheetMapperServiceImpl implements GoogleSheetMapperService {

    @Override
    public List<TranslationWord> mapResults(List<List<Object>> values) {
        return values == null
                ? Collections.emptyList()
                : values.stream()
                    .filter(item -> item.size() >= 4)
                    .map(GoogleSheetMapperServiceImpl::mapTranslationWord)
                    .collect(Collectors.toList());
    }

    private static TranslationWord mapTranslationWord(List<Object> item) {
        return new TranslationWord(getStringValue(item, 2), getStringValue(item, 3),
                getStringValue(item, 0), getStringValue(item, 1));
    }

    private static String getStringValue(List<Object> item, int index) {
        Object object = item.get(index);
        String value = null;
        if (object != null) {
            value = String.valueOf(object);
        }
        return value;
    }

}

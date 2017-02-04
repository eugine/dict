package com.eugenesokolov.dict;

import com.eugenesokolov.dict.model.TranslationWord;
import com.google.api.services.sheets.v4.Sheets;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpreadSheetReader {

    public List<TranslationWord> read(String spreadSheetId) {
        new Sheets.Builder()
        return null;
    }
}

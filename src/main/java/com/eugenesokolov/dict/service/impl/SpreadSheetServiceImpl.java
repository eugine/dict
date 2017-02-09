package com.eugenesokolov.dict.service.impl;

import com.eugenesokolov.dict.model.TranslationWord;
import com.eugenesokolov.dict.service.GoogleSheetMapperService;
import com.eugenesokolov.dict.service.GoogleSheetsService;
import com.eugenesokolov.dict.service.SpreadSheetService;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import io.reactivex.Observable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
class SpreadSheetServiceImpl implements SpreadSheetService {

    private static final String CELLS_RANGE = "A:D";

    @Resource
    private GoogleSheetsService googleSheetsService;
    @Resource
    private GoogleSheetMapperService googleSheetMapperService;

    @Override
    public Observable<TranslationWord> fromSpreadSheet(String documentId) {
        return Observable.fromCallable(googleSheetsService::getSheetsService)
                .map(gService -> extraValueRange(documentId, gService))
                .map(ValueRange::getValues)
                .map(googleSheetMapperService::mapResults)
                .flatMap(this::toObservableArray);
    }

    private ValueRange extraValueRange(String documentId, Sheets gService) throws java.io.IOException {
        return gService.spreadsheets().values().get(documentId, CELLS_RANGE).execute();
    }

    private Observable<TranslationWord> toObservableArray(List<TranslationWord> translationWords) {
        return Observable.fromArray(translationWords.toArray(new TranslationWord[0]));
    }
}

package com.eugenesokolov.dict.service;

import com.eugenesokolov.dict.model.TranslationWord;
import io.reactivex.Observable;

public interface SpreadSheetService {

    Observable<TranslationWord> fromSpreadSheet(String documentId);
}

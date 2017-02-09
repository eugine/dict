package com.eugenesokolov.dict.service;

import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;

public interface GoogleSheetsService {

    Sheets getSheetsService() throws IOException;
}

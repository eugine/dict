package com.eugenesokolov.dict;

import com.eugenesokolov.dict.model.TranslationWord;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow.Builder;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.getProperty;
import static java.util.Collections.singletonList;

@Component
public class SpreadSheetReader {
    private static final String APPLICATION_NAME = "Google Sheets API Loader";
    private static final java.io.File DATA_STORE_DIR = new java.io.File(getProperty("user.home"), ".google-api");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public static Credential authorize() throws IOException {
        InputStream in = new FileInputStream("/home/eugene/.key/google_api_client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    public static Sheets getSheetsService(Credential credential) throws IOException {
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = getSheetsService(authorize());

        String spreadsheetId = "1kUKnDEVsbbWzH3dMkkbErWipF3L9w90xXGQiCpPGJe0";
        String range = "A:D";
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<TranslationWord> words = mapResults(response.getValues());
        words.forEach(System.out::println);
    }

    private static List<TranslationWord> mapResults(List<List<Object>> values) {
        return values == null ? Collections.emptyList()
                : values.stream().filter(item -> item.size() >= 4)
                .map(SpreadSheetReader::mapTranslationWord)
                .collect(Collectors.toList());
    }

    private static TranslationWord mapTranslationWord(List<Object> item) {
        return new TranslationWord(getStringValue(item, 2), getStringValue(item, 3), getStringValue(item, 0), getStringValue(item, 1));
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

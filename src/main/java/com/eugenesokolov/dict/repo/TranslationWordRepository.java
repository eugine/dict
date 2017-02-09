package com.eugenesokolov.dict.repo;

import com.eugenesokolov.dict.model.TranslationWord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationWordRepository extends CrudRepository<TranslationWord, String> {

    TranslationWord findFirstBySourceAndSourceLang(String source, String sourceLang);
}

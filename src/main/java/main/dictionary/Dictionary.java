package main.dictionary;

import main.lookup.data.Definitions;

import java.util.List;

public interface Dictionary {
    List<Definitions> wordsToTranslations(String language);
}

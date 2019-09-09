package ua.mkorniie.model.enums;

import ua.mkorniie.model.exceptions.LanguageNotFoundException;

import java.util.Locale;

public enum Language {
    en,
    ua;

    public static Language of(Locale locale) throws LanguageNotFoundException {
        for (Language language : Language.values()) {
            if (locale.getLanguage().equals(language.name())) {
                return language;
            }
        }

        throw new LanguageNotFoundException("Unable to find available language for locale " + locale);
    }
}

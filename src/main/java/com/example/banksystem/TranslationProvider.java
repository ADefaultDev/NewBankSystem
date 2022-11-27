package com.example.banksystem;

import com.vaadin.flow.i18n.I18NProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TranslationProvider implements I18NProvider {

    public static final String BUNDLE_PREFIX = "translate";

    public final Locale LOCALE_FI = new Locale("fi", "FI");
    public final Locale LOCALE_EN = new Locale("en", "GB");

    private final List<Locale> locales = List.of(LOCALE_FI, LOCALE_EN);

    @Override
    public List<Locale> getProvidedLocales() {
        return locales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {


       return key;
    }
}

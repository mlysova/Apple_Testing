package com.apple.pages.testing.utils;

import com.browserup.bup.BrowserUpProxy;
import com.browserup.harreader.model.HarEntry;
import com.browserup.harreader.model.HarQueryParam;
import com.browserup.harreader.model.HttpStatus;

import java.util.List;

public class TestUtils {

    public static boolean is200CodeInResponse(String urlToCheck, boolean equals, BrowserUpProxy bmp) {
        List<HarEntry> list = bmp.getHar().getLog().getEntries();

        for (HarEntry entry : list) {
            if ((equals && entry.getRequest().getUrl().equals(urlToCheck) ||
                    (entry.getRequest().getUrl().startsWith(urlToCheck)))) {

                return entry.getResponse().getStatus() == HttpStatus.OK.getCode();
            }
        }
        return false;
    }

    public static boolean is200CodeInResponseByQueryParam(String param, String value, BrowserUpProxy bmp) {
        for (HarEntry entry : bmp.getHar().getLog().getEntries()) {
            for (HarQueryParam queryParam : entry.getRequest().getQueryString()) {
                if (queryParam.getName().equals(param) && queryParam.getValue().contains(value)) {
                    return entry.getResponse().getStatus() == HttpStatus.OK.getCode();
                }
            }
        }
        return false;
    }
}

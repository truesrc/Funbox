package ru.funbox.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.funbox.web.WebCtrl;

public class Utils {
    private static Logger log = LoggerFactory.getLogger(WebCtrl.class);

    public static String getDomainName(String url) {
        int start = url.indexOf("://");
        if (start < 0) start = 0;
        else start += 3;

        int end = url.indexOf("/", start);
        if (end < 0) end = url.length();
        String domainName = url.substring(start, end);

        end = domainName.indexOf("?");
        if (end > 0)
            domainName = domainName.substring(0, end);

        int port = domainName.indexOf(":");
        if (port >= 0) {
            domainName = domainName.substring(0, port);
        }
        log.info(domainName);
        return domainName;
    }
}

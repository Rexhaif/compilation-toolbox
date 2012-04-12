package org.abstractmeta.toolbox.compilation.compiler.impl;

import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;

public class TestHelper {

    @SuppressWarnings("unchecked")
    public static String getResourcePath(String resource, String match) throws Exception {
        Enumeration<URL> urls = TestHelper.class.getClassLoader().getResources(resource);
        for (URL url : Collections.list(urls)) {
            if (url.toString().contains(match)) {
                String result = url.getFile();
                if (result.indexOf('!') != -1) {
                    result = result.substring(0, result.indexOf('!'));
                }
                result = result.replace("file:", "");
                return URLDecoder.decode(result, "UTF-8");
            }
        }
        return null;
    }


}

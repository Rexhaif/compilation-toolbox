/**
 * Copyright 2011 Adrian Witas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

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
package org.abstractmeta.toolbox.compilation.compiler.util;


import org.testng.Assert;
import org.testng.annotations.Test;

import javax.tools.StandardLocation;

@Test
public class URIUtilTest {

    public void testURIUtil() {
        Assert.assertNotNull(new URIUtil());
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testBuildInvalidUri() {
        URIUtil.buildUri("sdf- /");
    }

    public void testBuildUriWithName() {

        Assert.assertEquals("string:///com/foo/Bar.java", URIUtil.buildUri(StandardLocation.SOURCE_OUTPUT, "com.foo", "Bar").toString());
        Assert.assertEquals("bytecode:///com/foo/Bar.class", URIUtil.buildUri(StandardLocation.CLASS_OUTPUT, "com.foo", "Bar").toString());

        Assert.assertEquals("bytecode:///URIUtilTest.class", URIUtil.buildUri(StandardLocation.CLASS_OUTPUT, "URIUtilTest").toString());
        Assert.assertEquals("sourcepath:///URIUtilTest", URIUtil.buildUri(StandardLocation.SOURCE_PATH, "URIUtilTest").toString());

    }

}

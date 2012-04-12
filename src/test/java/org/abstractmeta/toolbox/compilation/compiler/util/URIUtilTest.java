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

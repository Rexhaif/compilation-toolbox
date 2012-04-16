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


import org.abstractmeta.toolbox.compilation.compiler.registry.JavaFileObjectRegistry;
import org.abstractmeta.toolbox.compilation.compiler.registry.impl.JavaFileObjectRegistryImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.net.URI;


@Test
public class SimpleJavaFileManagerTest {

    public void testSimpleJavaFileManager() {
        SimpleJavaFileManager javaFileManager = new SimpleJavaFileManager(ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null), null, new JavaFileObjectRegistryImpl());
        Assert.assertNotNull(javaFileManager);
        Assert.assertNull(javaFileManager.getClassLoader());
    }


    public void testGetFileForInput() throws IOException {
        JavaFileObjectRegistry registry = new JavaFileObjectRegistryImpl();
        SimpleJavaFileManager javaFileManager = new SimpleJavaFileManager(ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null), null, registry);
        FileObject fileObject = javaFileManager.getFileForInput(StandardLocation.SOURCE_PATH, SimpleJavaFileManagerTest.class.getPackage().getName(), "Foo");
        Assert.assertNull(fileObject);
        registry.register(new JavaCodeFileObject(URI.create("string:///Test.java")));
        Assert.assertNotNull(javaFileManager.getFileForInput(StandardLocation.SOURCE_OUTPUT, "", "Test"));
        registry.unregister(URI.create("string:///Test.java"));
        Assert.assertNull(javaFileManager.getFileForInput(StandardLocation.SOURCE_OUTPUT, "", "Test"));

    }

    public void testGetFileForOutput() throws IOException {
        JavaFileObjectRegistry registry = new JavaFileObjectRegistryImpl();
        SimpleJavaFileManager javaFileManager = new SimpleJavaFileManager(ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null), null, registry);
        Assert.assertNotNull(javaFileManager.getJavaFileForOutput(StandardLocation.CLASS_OUTPUT, "com.Foo", JavaFileObject.Kind.CLASS, null));
        Assert.assertTrue(registry.isRegistered(URI.create("bytecode:///com/Foo.class")));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testGetFileForOutputExcpetion() throws IOException {
        JavaFileObjectRegistry registry = new JavaFileObjectRegistryImpl();
        SimpleJavaFileManager javaFileManager = new SimpleJavaFileManager(ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null), null, registry);
        Assert.assertNotNull(javaFileManager.getJavaFileForOutput(StandardLocation.SOURCE_PATH, "com.Foo", JavaFileObject.Kind.HTML, null));
    }
}

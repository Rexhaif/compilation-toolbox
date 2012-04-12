package org.abstractmeta.toolbox.compilation.compiler.impl;


import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Provides implementation of JavaCodeFileObject SimpleJavaFileObject.
 * This implementation stores compiled byte code for a given class.
 * <p>Java file object of this type is created by {@link javax.tools.JavaFileManager#getJavaFileForOutput}
 * to stores compiled byte-code for given internal class name.
 * </p>
 *
 * @author Adrian Witas
 */

public class JavaCodeFileObject extends SimpleJavaFileObject {

    private ByteArrayOutputStream byteCodeOutputStream;

    protected JavaCodeFileObject(URI uri) {
        super(uri, Kind.CLASS);
    }

    @Override
    public OutputStream openOutputStream() {
        byteCodeOutputStream = new ByteArrayOutputStream();
        return byteCodeOutputStream;
    }

    byte[] getByteCode() {
        return byteCodeOutputStream.toByteArray();
    }
}

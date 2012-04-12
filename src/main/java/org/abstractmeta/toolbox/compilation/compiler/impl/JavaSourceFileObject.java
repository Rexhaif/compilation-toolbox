package org.abstractmeta.toolbox.compilation.compiler.impl;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/**
 * Provides implementation of SimpleJavaFileObject.
 * This implementation stores java source code for a given class.
 * <p> Iterable of this type is supplied  {@link javax.tools.JavaCompiler#getTask(java.io.Writer, javax.tools.JavaFileManager, javax.tools.DiagnosticListener, Iterable, Iterable, Iterable)}
 * to create actual compilation task: {@link javax.tools.JavaCompiler.CompilationTask}
 * </p>
 * @author Adrian Witas
 */

public class JavaSourceFileObject extends SimpleJavaFileObject {

    private final CharSequence source;


    protected JavaSourceFileObject(URI uri, CharSequence source) {
        super(uri, Kind.SOURCE);
        this.source = source;
    }


    @Override
    public CharSequence getCharContent(final boolean ignoreEncodingErrors)  throws UnsupportedOperationException {
        if (source == null) {
            throw new IllegalStateException("source was null");
        }
        return source;
    }


}

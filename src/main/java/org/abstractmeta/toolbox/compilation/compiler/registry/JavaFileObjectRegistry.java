package org.abstractmeta.toolbox.compilation.compiler.registry;

import javax.tools.JavaFileObject;
import java.net.URI;
import java.util.Collection;

/**
 * Represents Java File Object registry.
 * This registry manages java sources and compiled byte codes.
 * @author Adrian Witas
 */

public interface JavaFileObjectRegistry {

    void register(JavaFileObject fileObject);

    boolean isRegistered(URI objectUri);

    JavaFileObject get(URI objectUri);

    void unregister(URI objectUri);

    Collection<JavaFileObject> get(JavaFileObject.Kind kind);

}

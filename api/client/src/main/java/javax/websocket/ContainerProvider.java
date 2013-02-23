/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package javax.websocket;

import java.util.ServiceLoader;

/**
 * Provider class that allows the developer to get a reference to
 * the implementation of the WebSocketContainer.
 * The provider class uses the 
 * <a href="http://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html">ServiceLoader</a> 
 * to load an implementation of ContainerProvider. Specifically, the fully qualified classname
 * of the container implementation of ContainerProvider must be listed in the 
 * META-INF/services/javax.websocket.ContainerProvider file in the implementation JAR file.
 *
 * @author dannycoward
 */
public abstract class ContainerProvider {
 
    /** 
     * Obtain a new instance of a WebSocketContainer. The method looks for the
     * ContainerProvider implementation class in the order listed in the META-INF/services/javax.websocket.ContainerProvider 
     * file, returning the WebSocketContainer implementation from the ContainerProvider implementation
     * that is not null.
     * @return an implementation provided instance of type WebSocketContainer
     */
    public static WebSocketContainer getWebSocketContainer() {
         WebSocketContainer wsc = null;
        for (ContainerProvider impl : ServiceLoader.load(ContainerProvider.class)) {
            wsc = impl.getContainer(WebSocketContainer.class);
            if (wsc != null) {
                return wsc;
            } 
        }
        if (wsc == null) {
            throw new RuntimeException("Could not find an implementation class.");
        } else {
            throw new RuntimeException("Could not find an implementation class with a non-null WebSocketContainer.");
        }
    }
 
    /**
     * Load the container implementation.
     * @param <T>
     * @param containerClass
     * @return 
     */
    protected abstract <T> T getContainer(Class<T> containerClass);
}


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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This method level annotation can be used to make a Java method receive incoming web socket messages. Each websocket
 * endpoint may only have one message handling method for each of the native websocket message formats: text, binary and pong. Methods
 * using this annotation are allowed to have
 * parameters of types described below, otherwise the container will generate an error at deployment time. <br>
 * The allowed parameters are:
 * <ol>
 * <li>Exactly one of any of the following choices</li>
 * <ul>
 * if the method is handling text messages:
 * <li> {@link java.lang.String} to receive the whole message</li>
 * <li> Java primitive or class equivalent to receive the whole message converted to that type</li>
 * <li> String and boolean pair to receive the message in parts</li>
 * <li> {@link java.io.Reader} to receive the whole message as a blocking stream</li>
 * <li>any object parameter for which the endpoint has a text decoder ({@link Decoder.Text} or
 * {@link Decoder.TextStream}).<br/>
 * if the method is handling binary messages:
 * <li> byte[] or {@link java.nio.ByteBuffer} to receive the whole message</li>
 * <li> byte[] and boolean pair, or {@link java.nio.ByteBuffer} and boolean pair to receive the message in parts</li>
 * <li> {@link java.io.InputStream} to receive the whole message as a blocking stream</li>
 * <li> any object parameter for which the endpoint has a binary decoder ({@link Decoder.Binary} or
 * {@link Decoder.BinaryStream}).</li>
 * if the method is handling pong messages:
 * <li> {@link PongMessage} for handling pong messages</li>
 * </ul>
 * <li> and Zero to n String or Java primitive parameters
 * annotated with the {@link javax.websocket.server.WebSocketPathParam} annotation for server endpoints.</li>
 * <li> and an optional {@link Session} parameter</li>
 * </ol>
 * <p/>
 * The parameters may be listed in any order.<br><br>
 * The method may have a non-void return type, in which case the web socket runtime must interpret this as a
 * web socket message to return to the peer. The allowed data types for this return type, other than void, are
 * String, ByteBuffer, byte[], any Java primitive or class equivalent, and anything for which there is an encoder. Developers should
 * note that if developer closes the session during the invokation of a method with a return type, the method will complete but the
 * return value will not be delivered to the remote endpoint. The send failure will be passed back into the endpoint's error handling method.<br><br>
 * <p/>
 * For example:
 * <pre>
 * <code>
 * &nbsp@WebSocketMessage
 * public void processGreeting(String message, Session session) {
 * &nbsp&nbspSystem.out.println("Greeting received:" + message);
 * }
 * </code>
 * </pre>
 * For example:
 * <pre>
 * <code>
 * &nbsp@WebSocketMessage
 * public void processUpload(byte[] b, boolean last, Session session) {
 * &nbsp&nbsp// process partial data here, which check on last to see if these is more on the way
 * }
 * </code>
 * </pre>
 * Developers should not continue to reference message objects of type {@link java.io.Reader}, {@link java.nio.ByteBuffer}
 * or {@link java.io.InputStream} after the annotated method has completed, since they
 * may be recycled by the implementation.
 * @author dannycoward
 * @since Draft 002
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebSocketMessage {

    /**
     * Specifies the maximum size of message in bytes that the method
     * this annotates will be able to process, or -1 to indicate
     * that there is no maximum. The default is -1.
     *
     * @return the maximum size in bytes.
     */
    public long maxMessageSize() default -1;
}
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
/**
 * The DefaultServerConfiguration is a concrete class that embodies all the configuration
 * parameters for an endpoint that is to be published as a server endpoint. Developers may
 * subclass this class in order to override the configuration behavior.
 * @author dannycoward
 */
public class DefaultServerConfiguration implements ServerEndpointConfiguration {
    private String path;
    private List<String> subprotocols = new ArrayList<String>();
    private List<String> extensions = new ArrayList<String>();
    private List<Encoder> encoders = new ArrayList<Encoder>();
    private List<Decoder> decoders = new ArrayList<Decoder>();

    /** For subclass implementations. */
    protected DefaultServerConfiguration() {

    }
    /** Creates a server configuration with the given URI. */
    public DefaultServerConfiguration(String path) {
        this.path = path;
    }
    /* Sets all the encoders that this configuration will support.*/
    public DefaultServerConfiguration setEncoders(List<Encoder> encoders) {
        this.encoders = encoders;
        return this;
    }

    /** Sets all the decoders that this configuration will support. */
    public DefaultServerConfiguration setDecoders(List<Decoder> decoders) {
        this.decoders = decoders;
        return this;
    }

    /** Sets all the subprotocols that this configuration will support. */
    public DefaultServerConfiguration setSubprotocols(List<String> subprotocols) {
        this.subprotocols = subprotocols;
        return this;
    }

    /** Sets all the extensions that this configuration will support.
     */
    public DefaultServerConfiguration setExtensions(List<String> extensions) {
        this.extensions = extensions;
        return this;
    }
    /** Return the Decoder implementations configured. These
     will be used by the container to decode incoming messages
     * into the expected custom objects on MessageListener.onMessage()
     * callbacks.
     */
    public List<Encoder> getEncoders() {
        return this.encoders;
    }
     /** Return the Decoder implementations configured. These
     will be used by the container to decode incoming messages
     * into the expected custom objects on MessageListener.onMessage()
     * callbacks.
     */
    public List<Decoder> getDecoders() {
        return this.decoders;
    }

    @Override
    public String getPath() {
        return path;
    }


    /** 
     * The default implementation of this method returns, the first subprotocol in the list sent by the client that
     * the server supports, or null if there isn't one none. Subclasses may provide custom algorithms based on other factors.
     * @param requestedSubprotocols
     * @return
     */
    public String getNegotiatedSubprotocol(List<String> requestedSubprotocols) {
        throw new RuntimeException("To implement");
    }

    /** Provides a simple algorithm to return the list of extensions this server will
     * use for the web socket session: the configuration returns a list containing all of the requested
     * extensions passed to this method that it supports, using the order in the requested
     * extensions, the empty list if none. Subclasses may provide custom algorithms based on other factors.
     * @param requestedExtensions
     * @return
     */

    public List<String> getNegotiatedExtensions(List<String> requestedExtensions) {
        throw new RuntimeException("To implement");
    }

    /** Makes a check of the validity of the Origin header sent along with the opening
     * handshake following the recommendation at: http://tools.ietf.org/html/rfc6455#section-4.2 .
     *
     * @param originHeaderValue
     * @return
     */
    public boolean checkOrigin(String originHeaderValue) {
        throw new RuntimeException("To implement");
    }

    /** This default implementation matches the incoming path to the configuration's URI or URI template if and only if
     * it is an exact match in the case the configuration is a URI, and if and only if it is a valid
     * expansion of the configuration URI template, in the case where the configuration is a URI template. Subclasses may override this method to provide
     * different matching policies.
     * @param uri
     * @return
     */
    public boolean matchesURI(URI uri) {
        return this.path.equals(uri.toString());
    }


    /** The default server configuration does not make any changes to the response. Subclasses may
     * override this method in order to inspect the Http request headers of the openinghandshake, for example to track cookies
     * sent by the client. Additionally subclasses may choose to override this method to modify the outgoing
     * handshake response.
     * the outgoing handshake response
     * @param request
     * @param response
     */
    public void modifyHandshake(HandshakeRequest request, HandshakeResponse response) {}


}
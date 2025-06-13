// Start of user code Copyright
/*
 * Copyright (c) 2020 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Simple
 */
// End of user code

package org.oasis.oslc.promcode.server;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import jakarta.ws.rs.core.UriBuilder;

import org.eclipse.lyo.oslc4j.core.model.Link;
import org.eclipse.lyo.oslc4j.core.OSLC4JUtils;
import org.eclipse.lyo.oslc.domains.promcode.Artifact;
import org.eclipse.lyo.oslc.domains.promcode.ManagedItem;
import org.eclipse.lyo.oslc.domains.promcode.Measure;
import org.eclipse.lyo.oslc.domains.promcode.Project;
import org.eclipse.lyo.oslc.domains.promcode.ScopeItem;
import org.eclipse.lyo.oslc.domains.promcode.WorkItem;

// Start of user code imports
// End of user code

// Start of user code pre_class_code
// End of user code

public class ResourcesFactory {

    private String basePath;

    // Start of user code class_attributes
    // End of user code

    public ResourcesFactory(String basePath) {
        this.basePath = basePath;
    }

    // Start of user code class_methods
    // End of user code

    //methods for Artifact resource
    
    public Artifact createArtifact(final String id) {
        return new Artifact(constructURIForArtifact(id));
    }
    
    public URI constructURIForArtifact(final String id) {
        Map<String, Object> pathParameters = new HashMap<String, Object>();
        pathParameters.put("id", id);
        String instanceURI = "artifact/{id}";
    
        final UriBuilder builder = UriBuilder.fromUri(this.basePath);
        return builder.path(instanceURI).buildFromMap(pathParameters);
    }
    
    public Link constructLinkForArtifact(final String id , final String label) {
        return new Link(constructURIForArtifact(id), label);
    }
    
    public Link constructLinkForArtifact(final String id) {
        return new Link(constructURIForArtifact(id));
    }
    

    //methods for Project resource
    
    public Project createProject(final String id) {
        return new Project(constructURIForProject(id));
    }
    
    public URI constructURIForProject(final String id) {
        Map<String, Object> pathParameters = new HashMap<String, Object>();
        pathParameters.put("id", id);
        String instanceURI = "project/{id}";
    
        final UriBuilder builder = UriBuilder.fromUri(this.basePath);
        return builder.path(instanceURI).buildFromMap(pathParameters);
    }
    
    public Link constructLinkForProject(final String id , final String label) {
        return new Link(constructURIForProject(id), label);
    }
    
    public Link constructLinkForProject(final String id) {
        return new Link(constructURIForProject(id));
    }
    

}

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


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContextEvent;
import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;
import org.eclipse.lyo.oslc4j.core.OSLC4JUtils;
import org.eclipse.lyo.oslc4j.core.model.AbstractResource;
import org.oasis.oslc.promcode.server.servlet.ServiceProviderCatalogSingleton;
import org.oasis.oslc.promcode.server.ServiceProviderInfo;
import org.eclipse.lyo.oslc.domains.promcode.Artifact;
import org.eclipse.lyo.oslc.domains.promcode.Measure;
import org.eclipse.lyo.oslc.domains.promcode.Project;
import org.eclipse.lyo.oslc.domains.promcode.ScopeItem;
import org.eclipse.lyo.oslc.domains.promcode.WorkItem;
import java.net.URI;
import java.util.NoSuchElementException;
import org.eclipse.lyo.store.ModelUnmarshallingException;
import org.eclipse.lyo.store.Store;
import org.eclipse.lyo.store.StorePool;
import org.eclipse.lyo.store.StoreAccessException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;



// Start of user code imports
// End of user code

// Start of user code pre_class_code
// End of user code

public class RestDelegate {

    private static final Logger log = LoggerFactory.getLogger(RestDelegate.class);

    @Inject StorePool storePool;
    
    @Inject ResourcesFactory resourcesFactory;
    // Start of user code class_attributes
    private static int scopeItemId = 1;
    private static int workItemId = 1;
    private static int artifactId = 1;
    private static int issueId = 1;
    private static int riskId = 1;
    private static int measureId = 1;
    private static int measurementId = 1;
    private static int projectId = 1;
    private static int planId = 1;
    private static int reportId = 1;
    private static int issueCollectionId = 1;
    private static int riskCollectionId = 1;

    private static ServiceProviderInfo sp = new ServiceProviderInfo();
    static {
        sp.name = "PROMCODE Service Provider";
        sp.serviceProviderId = "1";
    }
    // End of user code
    
    public RestDelegate() {
        log.trace("Delegate is initialized");
    }
    
    
    // Start of user code class_methods
    // End of user code

    //The methods contextInitializeServletListener() and contextDestroyServletListener() no longer exits
    //Migrate any user-specific code blocks to the class org.oasis.oslc.promcode.server.servlet.ServletListener
    //Any user-specific code should be found in *.lost files.

    public static ServiceProviderInfo[] getServiceProviderInfos(HttpServletRequest httpServletRequest)
    {
        ServiceProviderInfo[] serviceProviderInfos = {};
        
        // Start of user code "ServiceProviderInfo[] getServiceProviderInfos(...)"
        serviceProviderInfos = new ServiceProviderInfo[] {sp};
        // End of user code
        return serviceProviderInfos;
    }

    public List<Artifact> ArtifactSelector(HttpServletRequest httpServletRequest, String terms)
    {
        List<Artifact> resources = null;
        
        // Start of user code ArtifactSelector_storeInit
        // End of user code
        Store store = storePool.getStore();
        try {
            resources = new ArrayList<Artifact>(store.getResources(storePool.getDefaultNamedGraphUri(), Artifact.class, "", "", terms, 20, -1));
        } catch (StoreAccessException | ModelUnmarshallingException e) {
            log.error("Failed to search resources, with search-term '" + terms + "'", e);
            throw new WebApplicationException("Failed to search resources, with search-term '" + terms + "'", e, Status.INTERNAL_SERVER_ERROR);
        } finally {
            storePool.releaseStore(store);
        }
        // Start of user code ArtifactSelector_storeFinalize
        // End of user code
        
        // Start of user code ArtifactSelector
        // TODO Implement code to return a set of resources, based on search criteria 
        // An empty List should imply that no resources where found.
        // If you encounter problems, consider throwing the runtime exception WebApplicationException(message, cause, final httpStatus)
        // End of user code
        return resources;
    }
    public Artifact createArtifact(HttpServletRequest httpServletRequest, final Artifact aResource)
    {
        Artifact newResource = null;
        
        // Start of user code createArtifact_storeInit
        // End of user code
        Store store = storePool.getStore();
        try {
            URI uri = null;
            // Start of user code createArtifact_storeSetUri
            String id = Integer.toString(artifactId++);
            uri = resourcesFactory.constructURIForArtifact(id /* aResource.getIdentifier() */);
            aResource.setIdentifier(id);
            // End of user code
            if (store.resourceExists(storePool.getDefaultNamedGraphUri(), uri)) {
                log.error("Cannot create a resource that already exists: '" + uri + "'");
                throw new WebApplicationException("Cannot create a resource that already exists: '" + uri + "'", Status.SEE_OTHER);
            }
            aResource.setAbout(uri);
            try {
                store.appendResource(storePool.getDefaultNamedGraphUri(), aResource);
            } catch (StoreAccessException e) {
                log.error("Failed to create resource: '" + aResource.getAbout() + "'", e);            
                throw new WebApplicationException("Failed to create resource: '" + aResource.getAbout() + "'", e, Status.INTERNAL_SERVER_ERROR);
            }
        } finally {
            storePool.releaseStore(store);
        }
        newResource = aResource;
        // Start of user code createArtifact_storeFinalize
        // End of user code
        
        // Start of user code createArtifact
        // TODO Implement code to create a resource
        // If you encounter problems, consider throwing the runtime exception WebApplicationException(message, cause, final httpStatus)
        // End of user code
        return newResource;
    }



    public List<Project> ProjectSelector(HttpServletRequest httpServletRequest, String terms)
    {
        List<Project> resources = null;
        
        // Start of user code ProjectSelector_storeInit
        // End of user code
        Store store = storePool.getStore();
        try {
            resources = new ArrayList<Project>(store.getResources(storePool.getDefaultNamedGraphUri(), Project.class, "", "", terms, 20, -1));
        } catch (StoreAccessException | ModelUnmarshallingException e) {
            log.error("Failed to search resources, with search-term '" + terms + "'", e);
            throw new WebApplicationException("Failed to search resources, with search-term '" + terms + "'", e, Status.INTERNAL_SERVER_ERROR);
        } finally {
            storePool.releaseStore(store);
        }
        // Start of user code ProjectSelector_storeFinalize
        // End of user code
        
        // Start of user code ProjectSelector
        // TODO Implement code to return a set of resources, based on search criteria 
        // An empty List should imply that no resources where found.
        // If you encounter problems, consider throwing the runtime exception WebApplicationException(message, cause, final httpStatus)
        // End of user code
        return resources;
    }
    public Project createProject(HttpServletRequest httpServletRequest, final Project aResource)
    {
        Project newResource = null;
        
        // Start of user code createProject_storeInit
        // End of user code
        Store store = storePool.getStore();
        try {
            URI uri = null;
            // Start of user code createProject_storeSetUri
            String id = Integer.toString(projectId++);
            uri = resourcesFactory.constructURIForProject(id /* aResource.getIdentifier() */);
            aResource.setIdentifier(id);
            // End of user code
            if (store.resourceExists(storePool.getDefaultNamedGraphUri(), uri)) {
                log.error("Cannot create a resource that already exists: '" + uri + "'");
                throw new WebApplicationException("Cannot create a resource that already exists: '" + uri + "'", Status.SEE_OTHER);
            }
            aResource.setAbout(uri);
            try {
                store.appendResource(storePool.getDefaultNamedGraphUri(), aResource);
            } catch (StoreAccessException e) {
                log.error("Failed to create resource: '" + aResource.getAbout() + "'", e);            
                throw new WebApplicationException("Failed to create resource: '" + aResource.getAbout() + "'", e, Status.INTERNAL_SERVER_ERROR);
            }
        } finally {
            storePool.releaseStore(store);
        }
        newResource = aResource;
        // Start of user code createProject_storeFinalize
        // End of user code
        
        // Start of user code createProject
        // TODO Implement code to create a resource
        // If you encounter problems, consider throwing the runtime exception WebApplicationException(message, cause, final httpStatus)
        // End of user code
        return newResource;
    }




    public Artifact getArtifact(HttpServletRequest httpServletRequest, final String id)
    {
        Artifact aResource = null;
        
        // Start of user code getArtifact_storeInit
        // End of user code
        URI uri = resourcesFactory.constructURIForArtifact(id);
        // Start of user code getArtifact_storeSetUri
        // End of user code
        Store store = storePool.getStore();
        try {
            aResource = store.getResource(storePool.getDefaultNamedGraphUri(), uri, Artifact.class);
        } catch (NoSuchElementException e) {
            log.error("Resource: '" + uri + "' not found");
            throw new WebApplicationException("Failed to get resource: '" + uri + "'", e, Status.NOT_FOUND);
        } catch (StoreAccessException | ModelUnmarshallingException  e) {
            log.error("Failed to get resource: '" + uri + "'", e);
            throw new WebApplicationException("Failed to get resource: '" + uri + "'", e, Status.INTERNAL_SERVER_ERROR);
        } finally {
            storePool.releaseStore(store);
        }
        // Start of user code getArtifact_storeFinalize
        // End of user code
        
        // Start of user code getArtifact
        // TODO Implement code to return a resource
        // return 'null' if the resource was not found.
        // If you encounter problems, consider throwing the runtime exception WebApplicationException(message, cause, final httpStatus)
        // End of user code
        return aResource;
    }


    public Project getProject(HttpServletRequest httpServletRequest, final String id)
    {
        Project aResource = null;
        
        // Start of user code getProject_storeInit
        // End of user code
        URI uri = resourcesFactory.constructURIForProject(id);
        // Start of user code getProject_storeSetUri
        // End of user code
        Store store = storePool.getStore();
        try {
            aResource = store.getResource(storePool.getDefaultNamedGraphUri(), uri, Project.class);
        } catch (NoSuchElementException e) {
            log.error("Resource: '" + uri + "' not found");
            throw new WebApplicationException("Failed to get resource: '" + uri + "'", e, Status.NOT_FOUND);
        } catch (StoreAccessException | ModelUnmarshallingException  e) {
            log.error("Failed to get resource: '" + uri + "'", e);
            throw new WebApplicationException("Failed to get resource: '" + uri + "'", e, Status.INTERNAL_SERVER_ERROR);
        } finally {
            storePool.releaseStore(store);
        }
        // Start of user code getProject_storeFinalize
        // End of user code
        
        // Start of user code getProject
        // TODO Implement code to return a resource
        // return 'null' if the resource was not found.
        // If you encounter problems, consider throwing the runtime exception WebApplicationException(message, cause, final httpStatus)
        // End of user code
        return aResource;
    }



    public String getETagFromArtifact(final Artifact aResource)
    {
        String eTag = null;
        // Start of user code getETagFromArtifact
        // TODO Implement code to return an ETag for a particular resource
        // If you encounter problems, consider throwing the runtime exception WebApplicationException(message, cause, final httpStatus)
        // End of user code
        return eTag;
    }
    public String getETagFromProject(final Project aResource)
    {
        String eTag = null;
        // Start of user code getETagFromProject
        // TODO Implement code to return an ETag for a particular resource
        // If you encounter problems, consider throwing the runtime exception WebApplicationException(message, cause, final httpStatus)
        // End of user code
        return eTag;
    }

}

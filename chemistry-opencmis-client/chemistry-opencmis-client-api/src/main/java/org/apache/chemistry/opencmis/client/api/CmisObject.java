/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.chemistry.opencmis.client.api;

import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.AllowableActions;
import org.apache.chemistry.opencmis.commons.data.CmisExtensionElement;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.ExtensionLevel;

/**
 * Base CMIS object.
 * <p>
 * See CMIS Domain Model - section 2.1.2.
 */
public interface CmisObject extends ObjectId, CmisObjectProperties {

    // object

    /**
     * Returns the allowable actions if they have been fetched for this object.
     */
    AllowableActions getAllowableActions();

    /**
     * Returns the relationships if they have been fetched for this object.
     */
    List<Relationship> getRelationships();

    /**
     * Returns the ACL if it has been fetched for this object.
     */
    Acl getAcl();

    // object service

    /**
     * Deletes this object.
     * 
     * @param allVersions
     *            if this object is a document this parameter defines if just
     *            this version or all versions should be deleted
     */
    void delete(boolean allVersions);

    /**
     * Updates the properties that are provided.
     * 
     * @param properties
     *            the properties to update
     * 
     * @return the updated object (a repository might have created a new object)
     */
    CmisObject updateProperties(Map<String, ?> properties);

    /**
     * Updates the properties that are provided.
     * 
     * @param properties
     *            the properties to update
     * @param refresh
     *            indicates if the object should be refresh after the update
     * 
     * @return the object id of the updated object (a repository might have
     *         created a new object)
     * 
     */
    ObjectId updateProperties(Map<String, ?> properties, boolean refresh);

    // renditions

    /**
     * Returns the renditions if they have been fetched for this object.
     */
    List<Rendition> getRenditions();

    // policy service

    /**
     * Applies policies to this object.
     */
    void applyPolicy(ObjectId... policyIds);

    /**
     * Remove policies from this object.
     */
    void removePolicy(ObjectId... policyIds);

    /**
     * Returns the applied policies if they have been fetched for this object.
     */
    List<Policy> getPolicies();

    // ACL service

    /**
     * Adds and removes ACEs to the object.
     * 
     * @return the new ACL of this object
     */
    Acl applyAcl(List<Ace> addAces, List<Ace> removeAces, AclPropagation aclPropagation);

    /**
     * Adds ACEs to the object.
     */
    Acl addAcl(List<Ace> addAces, AclPropagation aclPropagation);

    /**
     * Removes ACEs to the object.
     */
    Acl removeAcl(List<Ace> removeAces, AclPropagation aclPropagation);

    // extensions

    /**
     * Returns the extensions for the given level.
     */
    List<CmisExtensionElement> getExtensions(ExtensionLevel level);

    // adapters

    /**
     * Returns an adapter based on the given interface.
     */
    <T> T getAdapter(Class<T> adapterInterface);

    /**
     * Returns a transient object adapter.
     * 
     * @see TransientCmisObject
     */
    TransientCmisObject getTransientObject();

    // session handling

    /**
     * Returns the timestamp (in milliseconds) of the last refresh.
     */
    long getRefreshTimestamp();

    /**
     * Reloads the data from the repository.
     */
    void refresh();

    /**
     * Reloads the data from the repository if the last refresh did not occur
     * within <code>durationInMillis</code>.
     */
    void refreshIfOld(long durationInMillis);
}

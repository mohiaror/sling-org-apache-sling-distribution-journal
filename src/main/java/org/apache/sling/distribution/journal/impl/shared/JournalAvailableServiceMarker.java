/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.distribution.journal.impl.shared;

import java.util.Objects;

import org.apache.sling.distribution.journal.JournalAvailable;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

class JournalAvailableServiceMarker implements JournalAvailable {

    private BundleContext context;
    private ServiceRegistration<JournalAvailable> reg;
    
    JournalAvailableServiceMarker(BundleContext context) {
        this.context = context;
    }
    
    synchronized void register() {
        if (this.reg == null) {
            this.reg = context.registerService(JournalAvailable.class, this, null);
            Objects.requireNonNull(this.reg); // To make incomplete mocking visible in tests
        }
    }

    synchronized void unRegister() {
        if (this.reg != null) {
            this.reg.unregister();
            this.reg = null;
        }
    }

    synchronized boolean isRegistered() {
        return this.reg != null;
    }
}

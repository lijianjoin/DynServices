/*
 *  Copyright "2024", Jian Li
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.data.RemoteServiceId;

public class RemoteServiceProviderUtils {

    private RemoteServiceProviderUtils() {
    }

    public static boolean isIdenticalLocation(RemoteServiceId location1, RemoteServiceId location2) {
        if (location1 == location2) return true;
        if (location1.getGroupId() != null ? !location1.getGroupId().equals(location2.getGroupId()) : location2.getGroupId() != null)
            return false;
        if (location1.getResourceId() != null ? !location1.getResourceId().equals(location2.getResourceId()) : location2.getResourceId() != null)
            return false;
        return (location1.getResourceVersion() != null ? location1.getResourceVersion().equals(location2.getResourceVersion()) : location2.getResourceVersion() == null);
    }
}

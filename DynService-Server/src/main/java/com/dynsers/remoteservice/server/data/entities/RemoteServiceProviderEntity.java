/*

Copyright Jian Li, lijianjoin@gmail.com,

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.dynsers.remoteservice.server.data.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dynservice_provider", schema = "dynservice-register")
public class RemoteServiceProviderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "groupid", nullable = false)
    private String groupId;

    @Column(name = "resourceid", nullable = false)
    private String resourceId;

    @Column(name = "serviceid", nullable = false)
    private String serviceId;

    @Column(name = "resource_version", nullable = false)
    private String resourceVersion;

    @Column(name = "service_version", nullable = false)
    private String serviceVersion;

    @Column(name = "detection_interval", nullable = false)
    private Integer detectionInterval;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "additional_info", nullable = true)
    private String additionalInfo;
}

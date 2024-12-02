/*

* Author: Jian Li, jian.li1@sartorius.com

*/
package com.dynsers.remoteservice.server.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Table(name = "service_provider", schema = "service-register")
@Accessors(chain = true)
public class RemoteServiceProviderEntity {

    // @GeneratedValue ALWAYS creating new Sequence number and overwrites provided ones that are already set at the
    // entity before saving
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

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "service_location", nullable = false)
    private String serviceLocation;

    @Column(name = "detection_interval", nullable = false)
    private Integer detectionInterval;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "additional_info")
    private String additionalInfo;
}

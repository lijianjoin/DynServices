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
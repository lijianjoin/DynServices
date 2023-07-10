package com.dynsers.remoteservice.server.repository;

import com.dynsers.remoteservice.server.data.entities.RemoteServiceProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RSRServiceProviderRepo extends JpaRepository<RemoteServiceProviderEntity, Integer>, CustomerRSRServiceProviderRepo{





}

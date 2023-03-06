package com.sanket.distributed_kv_store.repositories;

import com.sanket.distributed_kv_store.models.StoreEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntry, UUID> {
}

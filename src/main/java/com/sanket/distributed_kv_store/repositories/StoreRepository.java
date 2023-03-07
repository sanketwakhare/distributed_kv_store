package com.sanket.distributed_kv_store.repositories;

import com.sanket.distributed_kv_store.models.StoreEntry;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntry, UUID> {

    @Query("select e from StoreEntry e where e.key=:key")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<StoreEntry> findByKey(@Param("key") String key);
}

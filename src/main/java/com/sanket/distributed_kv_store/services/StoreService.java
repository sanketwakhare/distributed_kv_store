package com.sanket.distributed_kv_store.services;

import com.sanket.distributed_kv_store.models.StoreEntry;
import com.sanket.distributed_kv_store.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

// TODO: handle exceptions and refactor
// TODO: CRON job to clear/soft delete the entries
// TODO: use transaction for put method to maintain data integrity
// TODO: expose rest endpoints

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void put(String key, String value, int seconds) {
        Optional<StoreEntry> entry = storeRepository.findByKey(key);
        StoreEntry storeEntry;
        if (entry.isEmpty()) {
            storeEntry = new StoreEntry();
            storeEntry.setKey(key);
        } else {
            storeEntry = entry.get();
        }
        storeEntry.setValue(value);
        Date now = Calendar.getInstance().getTime();
        Instant ttl = now.toInstant().plusSeconds(seconds);
        storeEntry.setTtl(ttl.toEpochMilli());
        storeRepository.saveAndFlush(storeEntry);
    }


    public void put(String key, String value) {
        // default ttl is 1 day
        put(key, value, 60 * 60 * 24);
    }

    public void delete(String key) {
        Optional<StoreEntry> entry = storeRepository.findByKey(key);
        if (entry.isPresent()) {
            StoreEntry storeEntry = entry.get();
            if (storeEntry.getTtl() == -1) {
                // TODO: entry already deleted
                return;
            }
            // soft deleted ttl
            storeEntry.setTtl((long) -1);
            storeRepository.saveAndFlush(storeEntry);
        }
    }

    public String get(String key) {
        Optional<StoreEntry> entry = storeRepository.findByKey(key);
        if (entry.isPresent()) {
            StoreEntry storeEntry = entry.get();
            if (storeEntry.getTtl() == -1) {
                // entry expired
                return null;
            }
            Date now = Calendar.getInstance().getTime();
            if (storeEntry.getTtl() <= now.toInstant().toEpochMilli()) {
                // LAZY DELETION: EXPLICIT EXPIRE ON FETCH
                // explicitly expire the entry as this is already expired
                storeEntry.setTtl((long) -1);
                storeRepository.saveAndFlush(storeEntry);
                return null;
            }
            // return valid value
            return storeEntry.getValue();
        }
        // entry not present
        return null;
    }

    public void ttl(String key, int seconds) {
        Optional<StoreEntry> entry = storeRepository.findByKey(key);
        StoreEntry storeEntry;
        if (entry.isPresent()) {
            storeEntry = entry.get();
            Date now = Calendar.getInstance().getTime();
            Instant ttl = now.toInstant().plusSeconds(seconds);
            storeEntry.setTtl(ttl.toEpochMilli());
        } else {
            return;
        }
        storeRepository.saveAndFlush(storeEntry);
    }

}

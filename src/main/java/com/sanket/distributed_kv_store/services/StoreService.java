package com.sanket.distributed_kv_store.services;

import com.sanket.distributed_kv_store.exceptions.EntryNotPresentException;
import com.sanket.distributed_kv_store.models.StoreEntry;
import com.sanket.distributed_kv_store.models.StoreEntryStatus;
import com.sanket.distributed_kv_store.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

// TODO: CRON job to clear/soft delete the entries

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public StoreEntryStatus put(String key, String value, int seconds) {
        StoreEntryStatus status;
        Optional<StoreEntry> entry = storeRepository.findByKey(key);
        StoreEntry storeEntry;
        if (entry.isEmpty()) {
            // create new entry
            storeEntry = new StoreEntry();
            storeEntry.setKey(key);
            status = StoreEntryStatus.CREATED;
        } else {
            // update value
            storeEntry = entry.get();
            status = storeEntry.getTtl() == -1 ? StoreEntryStatus.CREATED : StoreEntryStatus.MODIFIED;
        }
        storeEntry.setValue(value);
        Date now = Calendar.getInstance().getTime();
        Instant ttl = now.toInstant().plusSeconds(seconds);
        storeEntry.setTtl(ttl.toEpochMilli());
        storeRepository.saveAndFlush(storeEntry);
        return status;
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public StoreEntryStatus put(String key, String value) {
        // default ttl is 1 day
        return put(key, value, 60 * 60 * 24);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean delete(String key) throws EntryNotPresentException {
        Optional<StoreEntry> entry = storeRepository.findByKey(key);
        if (entry.isPresent()) {
            StoreEntry storeEntry = entry.get();
            if (storeEntry.getTtl() == -1) {
                // entry already deleted
                throw new EntryNotPresentException(key);
            }
            // soft deleted ttl
            storeEntry.setTtl((long) -1);
            storeRepository.saveAndFlush(storeEntry);
            return true;
        }
        throw new EntryNotPresentException(key);
    }

    public String get(String key) {
        Optional<StoreEntry> entry = storeRepository.findByKeyNonTransactional(key);
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

    @Transactional
    public void ttl(String key, int seconds) throws EntryNotPresentException {
        Optional<StoreEntry> entry = storeRepository.findByKey(key);
        StoreEntry storeEntry;
        if (entry.isEmpty()) {
            throw new EntryNotPresentException(key);
        }
        storeEntry = entry.get();
        Date now = Calendar.getInstance().getTime();
        Instant ttl = now.toInstant().plusSeconds(seconds);
        storeEntry.setTtl(ttl.toEpochMilli());
        storeRepository.saveAndFlush(storeEntry);
    }

}

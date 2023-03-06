package com.sanket.distributed_kv_store.services;

import com.sanket.distributed_kv_store.models.StoreEntry;
import com.sanket.distributed_kv_store.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void createStore(String key, String value, Long ttl) {
        StoreEntry storeEntry = new StoreEntry();
        storeEntry.setKey(key);
        storeEntry.setValue(value);
        storeEntry.setTtl(ttl);
        storeRepository.saveAndFlush(storeEntry);
    }
}

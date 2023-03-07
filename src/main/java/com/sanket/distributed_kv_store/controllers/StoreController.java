package com.sanket.distributed_kv_store.controllers;

import com.sanket.distributed_kv_store.models.Pair;
import com.sanket.distributed_kv_store.models.StoreEntryStatus;
import com.sanket.distributed_kv_store.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("store")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("put")
    public ResponseEntity<?> put(@RequestBody(required = true) Pair pair) {
        try {
            StoreEntryStatus status = storeService.put(pair.getKey(), pair.getValue());
            return switch (status) {
                case CREATED -> new ResponseEntity<>("entry created", HttpStatus.CREATED);
                case MODIFIED -> new ResponseEntity<>("entry modified", HttpStatus.ACCEPTED);
                case ERROR -> new ResponseEntity<>("error creating/modifying entry", HttpStatus.INTERNAL_SERVER_ERROR);
            };
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

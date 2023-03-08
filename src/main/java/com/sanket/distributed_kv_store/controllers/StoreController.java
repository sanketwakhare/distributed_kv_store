package com.sanket.distributed_kv_store.controllers;

import com.sanket.distributed_kv_store.models.Pair;
import com.sanket.distributed_kv_store.models.ResponseData;
import com.sanket.distributed_kv_store.models.ResponseMessage;
import com.sanket.distributed_kv_store.models.StoreEntryStatus;
import com.sanket.distributed_kv_store.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("store")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("put")
    public ResponseEntity<?> put(@RequestBody Pair pair) {
        try {
            StoreEntryStatus status = storeService.put(pair.getKey(), pair.getValue());
            ResponseMessage responseMessage = new ResponseMessage();
            HttpStatus httpStatus = null;
            switch (status) {
                case CREATED -> {
                    responseMessage.setMessage("entry created");
                    httpStatus = HttpStatus.CREATED;
                }
                case MODIFIED -> {
                    responseMessage.setMessage("entry modified");
                    httpStatus = HttpStatus.ACCEPTED;
                }
            }
            return new ResponseEntity<>(responseMessage, httpStatus);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get")
    public ResponseEntity<?> get(@RequestParam(name = "key") String key) {
        try {
            String value = storeService.get(key);
            ResponseData responseData = new ResponseData();
            responseData.setData(value);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

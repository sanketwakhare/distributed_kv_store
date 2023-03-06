package com.sanket.distributed_kv_store;

import com.sanket.distributed_kv_store.services.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest
class DistributedKvStoreApplicationTests {

	private final StoreService storeService;

	@Autowired
	DistributedKvStoreApplicationTests(StoreService storeService) {
		this.storeService = storeService;
	}

	@Test
	void contextLoads() {
		storeService.createStore("test-123", "test_value-123", Instant.now().toEpochMilli());
		storeService.createStore("test-124", "test_value-124", Instant.now().toEpochMilli());
		storeService.createStore("test-125", "test_value-125", Instant.now().toEpochMilli());
		storeService.createStore("test-126", "test_value-126", Instant.now().toEpochMilli());
	}

}

package com.sanket.distributed_kv_store;

import com.sanket.distributed_kv_store.models.StoreEntry;
import com.sanket.distributed_kv_store.services.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest
class DistributedKvStoreApplicationTests {

	@Autowired
	private StoreService storeService;

	@Test
	void contextLoads() {

		StoreEntry storeEntry = storeService.createStore("test", "test_value", Instant.now().toEpochMilli());
		System.out.println(storeEntry);
	}

}

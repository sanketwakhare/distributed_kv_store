package com.sanket.distributed_kv_store;

import com.sanket.distributed_kv_store.exceptions.EntryNotPresentException;
import com.sanket.distributed_kv_store.services.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DistributedKvStoreApplicationTests {

	private final StoreService storeService;

	@Autowired
	DistributedKvStoreApplicationTests(StoreService storeService) {
		this.storeService = storeService;
	}

	@Test
	void contextLoads() throws EntryNotPresentException {
		storeService.put("test-123", "test_value-123");
		storeService.put("test-124", "test_value-124");
		storeService.put("test-125", "test_value-125");
		storeService.put("test-126", "test_value-126");

		storeService.get("test-123");
		storeService.ttl("test-123", 10);

		storeService.delete("test-124");
		storeService.put("test-125", "newvalue", 30);

		System.out.println(storeService.get("test-123"));
		System.out.println(storeService.get("test-124"));
		System.out.println(storeService.get("test-125"));
		System.out.println(storeService.get("test-126"));

	}

}

package com.realtime.auction.bid_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"eureka.client.enabled=false",
		"spring.cloud.config.enabled=false"
})
class BidServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}

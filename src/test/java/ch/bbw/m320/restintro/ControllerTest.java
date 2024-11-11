package ch.bbw.m320.restintro;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@ExtendWith(SpringExtension.class)
class ControllerTest implements WithAssertions {

	@Autowired
	private WebTestClient webClient;

	@Test
	void pingPong() {
		webClient.get()
				.uri("/api/ping")
				.exchange()
				.expectStatus()
				.is2xxSuccessful()
				.expectBody(String.class)
				.isEqualTo("pong");
	}

}

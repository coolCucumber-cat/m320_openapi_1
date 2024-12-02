package ch.bbw.m320.restintro;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import ch.bbw.m320.restintro.ArticleController.ArticleDto;
import ch.bbw.m320.restintro.ArticleController.NoMisinformationError;

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

	@Test
	void getArticles() {
		webClient.get()
				.uri("/api/article")
				.exchange()
				.expectStatus()
				.is2xxSuccessful();
	}

	@Test
	void createGoodArticle() {
		var articleGood = new ArticleDto("From the river to the sea", "Palestine will be free.");
		var req = webClient.post().uri("/api/article").bodyValue(articleGood);
		req.exchange().expectStatus().isCreated();
	}

	@Test
	void createBadArticle() {
		var articleBad = new ArticleDto("capitalism is good", "vuvuzela north korea stalin 100 million dead");
		var req = webClient.post().uri("/api/article").bodyValue(articleBad);
		req.exchange().expectStatus()
				.isBadRequest()
				.expectBody(NoMisinformationError.class);

	}
}

package ch.bbw.m320.restintro;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * just a smallish controller to outline basic concepts
 */
@CrossOrigin
@RestController
@RequestMapping("/api/article")
public class ArticleController {

	private final HashMap<Integer, ArticleDto> articles = new HashMap<>();

	public ArticleController() {
		ArticleDto newArticle = new ArticleDto("Why amogus is sus", "because it's the impostor",
				List.of("politics"));
		post(newArticle);
	}

	@GetMapping
	public List<ArticleDto> get() {
		return articles.values().stream().toList();
	}

	@PostMapping
	public ResponseEntity<ArticleDto> post(@RequestBody ArticleDto article) {
		if (article.headline.equals("capitalism is good")) {
			throw new NoMisinformationError();
		}
		var id = articles.size();
		ArticleDto newArticle = new ArticleDto(id, article);
		return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	static class NoMisinformationError extends IllegalArgumentException {
		public NoMisinformationError() {
			super("no misinformation allowed");
		}
	}

	public record ArticleDto(@Nullable int id, String headline, String content, List<String> tags, int likes,
			int dislikes,
			java.util.Date posted) {
		public ArticleDto(int id, ArticleDto article) {
			this(id, article.headline, article.content, article.tags, article.likes, article.likes, article.posted);
		}

		public ArticleDto(String headline, String content) {
			this(0, headline, content, List.of(), 0, 0, new Date());
		}

		public ArticleDto(String headline, String content, List<String> tags) {
			this(0, headline, content, tags, 0, 0, new Date());
		}

		public ArticleDto(String headline, String content, List<String> tags, int likes, int dislikes) {
			this(0, headline, content, tags, likes, dislikes, new Date());
		}

		public ArticleDto(String headline, String content, List<String> tags, int likes, int dislikes,
				java.util.Date posted) {
			this(0, headline, content, tags, likes, dislikes, posted);
		}
	}
}

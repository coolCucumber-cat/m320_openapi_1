package ch.bbw.m320.restintro;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		ArticleDto newArticle = new ArticleDto("ACAB", "Death to America",
				List.of("politics", "1312"));
		post(newArticle);
	}

	@GetMapping
	public List<ArticleDto> get() {
		return articles.values().stream().toList();
	}

	@PostMapping
	public ResponseEntity<ArticleDto> post(@RequestBody ArticleDto article) {
		if (NoMisinformationError.isMisinformation(article)) {
			throw new NoMisinformationError();
		}
		var id = articles.size();
		ArticleDto newArticle = new ArticleDto(id, article);
		articles.put(id, newArticle);
		return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
	}

	@PutMapping("/api/article/{id}")
	public ArticleDto put(@PathVariable int id, @RequestBody ArticleDto article) {
		// if (NoMisinformationError.isMisinformation(article)) {
		// throw new NoMisinformationError();
		// }
		var newArticle = new ArticleDto(id, article);
		articles.put(id, newArticle);
		return newArticle;
	}
	// @PatchMapping("/api/article/{id}")
	// public ArticleDto patch(@PathVariable int id, @RequestBody ArticleDto
	// article) {
	// // if (NoMisinformationError.isMisinformation(article)) {
	// // throw new NoMisinformationError();
	// // }
	// var newArticle = new ArticleDto(id, article);
	// articles.put(id, newArticle);
	// return newArticle;
	// }
	// @PatchMapping("/api/article/{id}")
	// public ResponseEntity<ArticleDto> patch(@PathVariable int id, @RequestBody
	// ArticleDto article) {
	// if (NoMisinformationError.isMisinformation(article)) {
	// throw new NoMisinformationError();
	// }
	// var newArticle = new ArticleDto(id, article);
	// articles.put(newArticle.id, newArticle);
	// return ResponseEntity.status(HttpStatus.OK).body(newArticle);
	// }

	@DeleteMapping("/api/article/{id}")
	public ResponseEntity<ArticleDto> delete(@PathVariable int id) {
		articles.remove(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	static class NoMisinformationError extends IllegalArgumentException {
		public NoMisinformationError() {
			super("no misinformation allowed");
		}

		static boolean isMisinformation(ArticleDto article) {
			return article.headline.equals("capitalism is good");
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

		public ArticleDto(int id, String headline, String content) {
			this(id, headline, content, List.of(), 0, 0, new Date());
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

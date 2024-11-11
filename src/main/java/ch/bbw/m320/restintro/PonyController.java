package ch.bbw.m320.restintro;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/ponies")
public class PonyController {

	private final List<PonyDto> list = new ArrayList<>();

	public PonyController() {
		// we fill the "database" with initial data.
		list.add(new PonyDto("Thunder", "Isabell"));
	}

	@GetMapping
	public List<PonyDto> getPonies() {
		return list;
	}

	@PostMapping
	public ResponseEntity<String> newPony(@RequestBody PonyDto newPony) {
		if (newPony.color.equals("ugly")) {
			throw new NoUglyPoniesException();
		}
		list.add(newPony);
		return ResponseEntity.status(HttpStatus.CREATED).header("X-Fuu", "Bar").body("totally worked");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	static class NoUglyPoniesException extends IllegalArgumentException {

		public NoUglyPoniesException() {
			super("ponies are not ugly");
		}
	}

	public record PonyDto(String name, String color) {}
}

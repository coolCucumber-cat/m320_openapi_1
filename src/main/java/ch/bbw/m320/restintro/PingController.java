package ch.bbw.m320.restintro;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/ping")
public class PingController {
	@GetMapping
	public String get() {
		return "pong";
	}
}

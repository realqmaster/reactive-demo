package it.my.demo;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("reactive")
public class ReactiveController {


	@GetMapping(value = "messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getMessages() {
		return Flux.interval(Duration.ofMillis(1000)).map(i -> {
			return "Message " + i;
		});
	}
}

package it.my.demo;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("reactive")
public class ReactiveController {

	@Autowired
	private MessageEmitter emitter;

	@GetMapping(value = "messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getMessages() {
		return Flux.interval(Duration.ofMillis(1000)).map(i -> {
			return "Message " + i;
		});
	}

	@GetMapping(value = "emitter", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getDynamicQueueMessages() {
		return Flux.from(emitter.getProcessor());
	}
}

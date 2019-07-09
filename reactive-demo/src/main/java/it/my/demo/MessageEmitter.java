package it.my.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

@Component
@EnableScheduling
public class MessageEmitter {

	private FluxProcessor<String, String> processor;

	public FluxProcessor<String, String> getProcessor() {
		return processor;
	}
	
	public MessageEmitter() {
		DirectProcessor<String> proc = DirectProcessor.create();
		setProcessor(proc);
		setSink(proc.sink());
	}

	public void setProcessor(FluxProcessor<String, String> processor) {
		this.processor = processor;
	}

	public FluxSink<String> getSink() {
		return sink;
	}

	public void setSink(FluxSink<String> sink) {
		this.sink = sink;
	}

	private FluxSink<String> sink;

	@Scheduled(fixedDelay = 1000)
	private void randomMessage() {
		Random r = new Random();
		if (r.nextBoolean()) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			sink.next("Un messaggio a caso emesso alle " + dtf.format(now));
		}
	}

}

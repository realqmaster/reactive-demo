package it.my.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.Disposable;

@SpringBootApplication
@EnableWebFlux
public class ReactiveDemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		List<String> inbox = new ArrayList<>();
		
		WebClient cli = WebClient.create();
		fillInbox(inbox, cli);
		System.out.println("Emptying inbox to fill a second time!");
		inbox.clear();
		fillInbox(inbox, cli);

	}

	private void fillInbox(List<String> inbox, WebClient cli) {
		Disposable d = cli
		.get()
		.uri("http://localhost:8080/reactive/messages")
		.retrieve()
		.bodyToFlux(String.class)
		.subscribe(m ->{
			System.out.println(m);
			inbox.add(m);
			}
		);

		System.out.println("Subscribed to reactive endpoint");
		
		while(!d.isDisposed()) {
			if(inbox.size() >= 10) {
				System.out.println("Inbox contains " + inbox.size() + ", unsubscribing!");
				d.dispose();
			}
		}
	}
}

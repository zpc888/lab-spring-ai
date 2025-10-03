package study;

import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@SpringBootApplication
public class SpringAiWorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiWorkshopApplication.class, args);
	}

    @Bean
    public SimpleLoggerAdvisor loggerAdvisor() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        return new SimpleLoggerAdvisor();
    }
}

package study.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {
    private final ChatClient chatClient;
    private final SimpleLoggerAdvisor loggerAdvisor;

    public ChatController(ChatClient.Builder chatClientBuilder, SimpleLoggerAdvisor loggerAdvisor) {
        this.chatClient = chatClientBuilder.build();
        this.loggerAdvisor = loggerAdvisor;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(defaultValue = "Tell me an interesting fact about Java") String message) {
        return chatClient.prompt()
                .user("Tell me an interesting fact about Java")
                .advisors(loggerAdvisor)
                .call()
                .content();
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam(defaultValue = "I am visiting Shanghai China soon, can you give me 10 places I must visit?") String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }

    @GetMapping("/joke")
    public ChatResponse joke(@RequestParam(defaultValue = "Tell me a joke about programming") String message) {
        return chatClient.prompt()
                .user(message)
                .call().chatResponse();
    }
}

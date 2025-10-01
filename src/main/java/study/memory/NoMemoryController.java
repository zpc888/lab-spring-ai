package study.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoMemoryController {
    private final ChatClient chatClient;

    public NoMemoryController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/no-memory")
    public String noMemory(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }


}

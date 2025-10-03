package study.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMemoryController {
    private final ChatClient chatClient;
    private final SimpleLoggerAdvisor loggerAdvisor;

    public ChatMemoryController(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, SimpleLoggerAdvisor loggerAdvisor) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
        this.loggerAdvisor = loggerAdvisor;
    }

    @GetMapping("/memory")
    public String memory(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .advisors(loggerAdvisor)
                .call()
                .content();
    }


}

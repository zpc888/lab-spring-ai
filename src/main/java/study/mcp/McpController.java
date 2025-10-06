package study.mcp;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class McpController {
    private final ChatClient chatClient;
    private final SimpleLoggerAdvisor loggerAdvisor;

    public McpController(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, ToolCallbackProvider tools, SimpleLoggerAdvisor loggerAdvisor) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultSystem("Please prioritise context information for answering questions")
                .defaultToolCallbacks(tools)
                .build();
        this.loggerAdvisor = loggerAdvisor;
    }

    @GetMapping("/mcp")
    public String mcp(@RequestParam(defaultValue = "How many sessions in spring io 2025 in total?") String question) {
        return chatClient.prompt()
                .user(question)
                .advisors(loggerAdvisor)
                .call()
                .content();
    }
}

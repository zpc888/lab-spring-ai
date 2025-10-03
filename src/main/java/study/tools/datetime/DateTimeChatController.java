package study.tools.datetime;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateTimeChatController {
    private final ChatClient chatClient;

    public DateTimeChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/datetime-no-tools")
    public String datetimeNoTools(
            @RequestParam(defaultValue = "What day is tomorrow?") String message) {
        return chatClient.prompt()
                .user(message)
//                .tools(new DateTimeTools())
                .call()
                .content();
    }

    @GetMapping("/datetime-with-tools")
    public String datetime(
            @RequestParam(defaultValue = "What day is tomorrow?") String message) {
        return chatClient.prompt()
                .user(message)
                .tools(new DateTimeTools())
                .call()
                .content();
    }
}

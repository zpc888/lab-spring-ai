package study.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {
    private final ChatClient chatClient;
    private final SimpleLoggerAdvisor loggerAdvisor;

    public RagController(ChatClient.Builder chatClientBuilder, VectorStore vectorStore, SimpleLoggerAdvisor loggerAdvisor) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
        this.loggerAdvisor = loggerAdvisor;
    }

    @GetMapping("/rag")
    public Models rag(@RequestParam(defaultValue = "Give me a list of all the models from OpenAI and their context window size") String message
            , @RequestParam(defaultValue = "false") boolean debug) {
        ChatClient.ChatClientRequestSpec reqSpec = chatClient.prompt()
                .user(message);
        if (debug) {
            reqSpec.advisors(loggerAdvisor);
        }
        return reqSpec.call()
            .entity(Models.class);
    }
}

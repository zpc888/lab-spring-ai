package study.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagMimicController {
    private final ChatClient chatClient;

    public RagMimicController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/models")
    public String models() {
        return chatClient.prompt()
                .user("""
                        Can you give me an up to date list popular large language models and their current context windows?
                        """)
                .call()
                .content();
    }

    @GetMapping("/models-ragged")
    public String modelsRagged() {
        var system = """
                If you're asked about up to date language models and their context windows, here is some information to help you with your answer:
                [
                    { "company": "OpenAI", "model": "GPT-4", "context_window": "8192 tokens" },
                    { "company": "OpenAI", "model": "GPT-4-turbo", "context_window": "128k tokens" },
                    { "company": "OpenAI", "model": "GPT-3.5-turbo", "context_window": "4096 tokens" },
                    { "company": "Anthropic", "model": "Claude-2", "context_window": "100k tokens" },
                    { "company": "Cohere", "model": "Gemini Pro", "context_window": "100k tokens" },
                    { "company": "Google DeepMind", "model": "Gemini 1.5 Pro", "context_window": "128k tokens" },
                    { "company": "Google DeepMind", "model": "Gemini 1.5", "context_window": "32k tokens" },
                    { "company": "Alibaba Cloud", "model": "Qen 2.5 72B", "context_window": "128k tokens" },
                    { "company": "Meta", "model": "Llama 3", "context_window": "32k tokens" },
                    { "company": "Meta", "model": "Llama 3 70B Chat", "context_window": "32k tokens" },
                    { "company": "Microsoft", "model": "Sydney (based on GPT-4)", "context_window": "8192 tokens" }
                ]
                """;
        return chatClient.prompt()
                .system(system)
                .user("""
                        Can you give me an up to date list popular large language models and their current context windows?
                        """)
                .call()
                .content();
    }
}

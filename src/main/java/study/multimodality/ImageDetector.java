package study.multimodality;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageDetector {
    private final ChatClient chatClient;
    @Value("classpath:images/clayton-cardinalli.jpg")
    Resource sampleImage;

    public ImageDetector(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/image-to-text")
    public String imageToText() {
        return chatClient.prompt()
                .user(u -> {
                    u.text("Can you please describe what you see in the following image?")
                            .media(MimeTypeUtils.IMAGE_JPEG, sampleImage);

                })
                .call()
                .content();
    }

}

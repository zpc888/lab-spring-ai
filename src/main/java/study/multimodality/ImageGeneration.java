package study.multimodality;

import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ImageGeneration {
    private final OpenAiImageModel imageModel;

    public ImageGeneration(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @GetMapping("/image-gen")
    public ResponseEntity<Map<String, String>> generateImage
            (@RequestParam(defaultValue = "A scenic view of sunset over mountains") String prompt) {

//        prompt = """
//Summary:
//trifold board advertising YM school's chemistry club
//
//Details:
//- It has to have bold letters in red which will likely be cut out of red construction papers into bubble letters
//- The title "YM Chemistry Club" will display at the top in the middle of the tri-fold board
//- there should be some representations of chemistry pictures on the side such as the bohr diagram or cylinder flask with a reaction coming out
//- On the left side, there should be the experiments my club's going to host displayed; this includes making edible rock candy, lemon battery,etc.
//- Please make it attractive.
//- Please make it looks like a real one.
//- Please make it colorful with blue, red, dark, etc
//- Please make QR code bigger on the bottom of right side
//- Please make real edible rock candy picture on the left side
//- Please make real lemon battery picture on the left side
//- Please make it less crowded
//
//                """;


        ImageOptions options = OpenAiImageOptions.builder()
                .model("dall-e-3")
                .width(1024)
                .height(1024)
                .quality("hd")
                .style("vivid")
                .build();

        ImagePrompt imgPrompt = new ImagePrompt(prompt, options);
        ImageResponse res = imageModel.call(imgPrompt);

        String url = res.getResult().getOutput().getUrl();
        return ResponseEntity.ok(Map.of("prompt", prompt, "imageUrl", url));
    }
}

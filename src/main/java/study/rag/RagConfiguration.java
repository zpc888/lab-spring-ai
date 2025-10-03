package study.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.List;

@Configuration
public class RagConfiguration {
    private static final Logger log = LoggerFactory.getLogger(RagConfiguration.class);

    private final String vectorStoreName = "my-vector-store.json";

    @Value("classpath:rag-data/models.json")
    private Resource ragData;

    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore ret = SimpleVectorStore.builder(embeddingModel).build();
        File file = new File(new File("src/main/resources/").getAbsoluteFile(), vectorStoreName);
        if (file.exists()) {
            log.info("Loading vector store from {}", file.getAbsolutePath());
            ret.load(file);
        } else {
            log.info("Creating vector store from {}", vectorStoreName);
            TextReader textReader = new TextReader(ragData);
            textReader.getCustomMetadata().put("filename", ragData.getFilename());
            List<Document> documents = textReader.get();
            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
            List<Document> splitDocs = tokenTextSplitter.apply(documents);
            ret.add(splitDocs);
            ret.save(file);
        }
        return ret;
    }
}

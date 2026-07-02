package com.example.policyassistant.rag;

import com.example.policyassistant.domain.PolicyChunk;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class PolicyKnowledgeBase {

    private final List<PolicyChunk> chunks;

    public PolicyKnowledgeBase() throws IOException {
        this.chunks = loadChunks();
    }

    public List<PolicyChunk> allChunks() {
        return List.copyOf(chunks);
    }

    private List<PolicyChunk> loadChunks() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:policies/*.md");
        List<PolicyChunk> loaded = new ArrayList<>();
        for (Resource resource : resources) {
            String filename = resource.getFilename();
            if (filename == null) {
                continue;
            }
            String documentId = filename.replace(".md", "");
            String content = resource.getContentAsString(StandardCharsets.UTF_8);
            String title = extractTitle(content, documentId);
            loaded.add(new PolicyChunk(documentId, title, content.trim()));
        }
        return loaded;
    }

    private String extractTitle(String content, String fallback) {
        for (String line : content.lines().toList()) {
            if (line.startsWith("# ")) {
                return line.substring(2).trim();
            }
        }
        return fallback;
    }
}

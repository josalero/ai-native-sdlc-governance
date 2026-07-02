package com.example.policyassistant.rag;

import com.example.policyassistant.config.AiProperties;
import com.example.policyassistant.domain.PolicyChunk;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class PolicyRetrievalService {

    private final PolicyKnowledgeBase knowledgeBase;
    private final AiProperties properties;

    public PolicyRetrievalService(PolicyKnowledgeBase knowledgeBase, AiProperties properties) {
        this.knowledgeBase = knowledgeBase;
        this.properties = properties;
    }

    private static final List<String> STOPWORDS =
            List.of("the", "and", "for", "what", "how", "when", "where", "who", "are", "is", "can", "does", "with");

    public List<PolicyChunk> retrieve(String question) {
        String normalizedQuestion = question.toLowerCase(Locale.ROOT);
        List<ScoredChunk> scored = new ArrayList<>();
        for (PolicyChunk chunk : knowledgeBase.allChunks()) {
            int score = score(normalizedQuestion, chunk);
            if (score > 0) {
                scored.add(new ScoredChunk(chunk, score));
            }
        }
        scored.sort(Comparator.comparingInt(ScoredChunk::score).reversed());
        return scored.stream()
                .limit(properties.maxRetrievedChunks())
                .map(ScoredChunk::chunk)
                .toList();
    }

    private int score(String question, PolicyChunk chunk) {
        int score = 0;
        String haystack = (chunk.title() + " " + chunk.content()).toLowerCase(Locale.ROOT);
        for (String token : question.split("\\W+")) {
            if (token.length() < 3 || STOPWORDS.contains(token)) {
                continue;
            }
            if (haystack.contains(token)) {
                score += token.length();
            }
        }
        return score;
    }

    private record ScoredChunk(PolicyChunk chunk, int score) {}
}

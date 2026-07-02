package com.example.policyassistant.rag;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PolicyRetrievalServiceTest {

    @Autowired
    private PolicyRetrievalService retrievalService;

    @Test
    void retrievesPtoPolicyForPtoQuestion() {
        var chunks = retrievalService.retrieve("How many PTO days per year?");
        assertThat(chunks).isNotEmpty();
        assertThat(chunks.getFirst().documentId()).isEqualTo("pto-policy");
    }
}

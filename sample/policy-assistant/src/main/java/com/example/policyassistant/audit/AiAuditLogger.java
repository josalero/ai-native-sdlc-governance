package com.example.policyassistant.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AiAuditLogger {

    private static final Logger log = LoggerFactory.getLogger("ai.audit");

    public void log(AiAuditEvent event) {
        log.info(
                "requestId={} userId={} useCase={} promptVersion={} model={} retrievedDocs={} retrievedDocIds={} "
                        + "outputValidation={} safetyValidation={} latencyMs={}",
                event.requestId(),
                event.userId(),
                event.useCaseId(),
                event.promptVersion(),
                event.modelName(),
                event.retrievedDocumentCount(),
                event.retrievedDocumentIds(),
                event.outputValidationResult(),
                event.safetyValidationResult(),
                event.latencyMs());
    }
}

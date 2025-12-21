package com.ezaybytes.accounts.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("auditAwareImpl") //auditAwareImpl is bean we have given. it can be anything.
public class AuditAwareImpl implements AuditorAware<String>{

    /**
     * Returns the current auditor of application
     * 
     * @return the current auditor.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS"); //MS -> microService
    }
    
}

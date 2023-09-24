package com.thorben.janssen.spring.data.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    @Bean
    public AuditingEntityListener auditingListener() {
        return new AuditingEntityListener();
    }

    public static class AuditorAwareImpl implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            // Example implementation using Spring Security
            // return Optional.ofNullable(SecurityContextHolder.getContext())
            //                .map(SecurityContext::getAuthentication)
            //                .filter(Authentication::isAuthenticated)
            //                .map(Authentication::getPrincipal)
            //                .map(User.class::cast);

            String username = "thorben.janssen";
            return Optional.of(username);
        }
    }
}

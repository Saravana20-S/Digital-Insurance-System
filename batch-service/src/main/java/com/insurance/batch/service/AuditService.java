package com.insurance.batch.service;

import com.insurance.batch.entity.AuditLog;
import com.insurance.batch.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void log(
            String username,
            String action,
            String serviceName,
            String status,
            String details) {

        AuditLog auditLog = AuditLog.builder()
                .username(username)
                .action(action)
                .serviceName(serviceName)
                .status(status)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);
    }
}
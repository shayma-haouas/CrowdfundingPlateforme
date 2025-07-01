package com.example.backend.Service;

import com.example.backend.DTO.UserActivityLogDTO;
import com.example.backend.Entities.User;
import com.example.backend.Entities.UserActivityLog;
import com.example.backend.Repository.UserActivityLogRepository;
import com.example.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserActivityService {
    @Autowired
    private UserActivityLogRepository activityLogRepo;

    @Autowired
    private UserRepository userRepo;

    public void logActivity(Long userId, UserActivityLogDTO dto) {
        User user = userRepo.findById(userId).orElseThrow();

        UserActivityLog log = new UserActivityLog();
        log.setUser(user);
        log.setSessionId(dto.getSessionId());
        log.setNetworkPacketSize(dto.getNetworkPacketSize());
        log.setProtocolType(dto.getProtocolType());
        log.setLoginAttempts(dto.getLoginAttempts());
        log.setSessionDuration(dto.getSessionDuration());
        log.setEncryptionUsed(dto.getEncryptionUsed());
        log.setIpReputationScore(dto.getIpReputationScore());
        log.setFailedLogins(dto.getFailedLogins());
        log.setBrowserType(dto.getBrowserType());
        log.setUnusualTimeAccess(dto.isUnusualTimeAccess());

        activityLogRepo.save(log);
    }

    public List<UserActivityLog> getAllActivities() {
        return activityLogRepo.findAll();
    }

}


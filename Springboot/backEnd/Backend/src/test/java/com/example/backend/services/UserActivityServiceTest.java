package com.example.backend.Service;

import com.example.backend.DTO.UserActivityLogDTO;
import com.example.backend.Entities.User;
import com.example.backend.Entities.UserActivityLog;
import com.example.backend.Repository.UserActivityLogRepository;
import com.example.backend.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserActivityServiceTest {

    @Mock
    private UserActivityLogRepository activityLogRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserActivityService userActivityService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogActivity_SavesLog() {
        Long userId = 1L;
        UserActivityLogDTO dto = new UserActivityLogDTO();
        dto.setSessionId("sess123");
        dto.setNetworkPacketSize(1234);
        dto.setProtocolType("TCP");
        dto.setLoginAttempts(2);
        dto.setSessionDuration(300);
        dto.setEncryptionUsed(true);
        dto.setIpReputationScore(85);
        dto.setFailedLogins(1);
        dto.setBrowserType("Chrome");
        dto.setUnusualTimeAccess(true);

        User user = new User();
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        userActivityService.logActivity(userId, dto);

        ArgumentCaptor<UserActivityLog> captor = ArgumentCaptor.forClass(UserActivityLog.class);
        verify(activityLogRepo).save(captor.capture());

        UserActivityLog savedLog = captor.getValue();
        assertEquals(user, savedLog.getUser());
        assertEquals(dto.getSessionId(), savedLog.getSessionId());
        assertEquals(dto.getNetworkPacketSize(), savedLog.getNetworkPacketSize());
        assertEquals(dto.getProtocolType(), savedLog.getProtocolType());
        assertEquals(dto.getLoginAttempts(), savedLog.getLoginAttempts());
        assertEquals(dto.getSessionDuration(), savedLog.getSessionDuration());
        assertEquals(dto.isEncryptionUsed(), savedLog.isEncryptionUsed());
        assertEquals(dto.getIpReputationScore(), savedLog.getIpReputationScore());
        assertEquals(dto.getFailedLogins(), savedLog.getFailedLogins());
        assertEquals(dto.getBrowserType(), savedLog.getBrowserType());
        assertEquals(dto.isUnusualTimeAccess(), savedLog.isUnusualTimeAccess());
    }

    @Test
    void testGetAllActivities_ReturnsList() {
        List<UserActivityLog> logs = List.of(new UserActivityLog(), new UserActivityLog());
        when(activityLogRepo.findAll()).thenReturn(logs);

        List<UserActivityLog> result = userActivityService.getAllActivities();

        assertEquals(2, result.size());
        verify(activityLogRepo).findAll();
    }
}

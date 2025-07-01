package com.example.backend.DTO;

import lombok.Data;

@Data
public class UserActivityLogDTO {
    private String sessionId;
    private int networkPacketSize;
    private String protocolType;
    private int loginAttempts;
    private double sessionDuration;
    private String encryptionUsed;
    private double ipReputationScore;
    private int failedLogins;
    private String browserType;
    private boolean unusualTimeAccess;
    private Boolean attackDetected; // Utilisation de Boolean au lieu de boolean

    public Boolean getAttackDetected() {
        return attackDetected;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getNetworkPacketSize() {
        return networkPacketSize;
    }

    public void setNetworkPacketSize(int networkPacketSize) {
        this.networkPacketSize = networkPacketSize;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public double getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(double sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public String getEncryptionUsed() {
        return encryptionUsed;
    }

    public void setEncryptionUsed(String encryptionUsed) {
        this.encryptionUsed = encryptionUsed;
    }

    public double getIpReputationScore() {
        return ipReputationScore;
    }

    public void setIpReputationScore(double ipReputationScore) {
        this.ipReputationScore = ipReputationScore;
    }

    public int getFailedLogins() {
        return failedLogins;
    }

    public void setFailedLogins(int failedLogins) {
        this.failedLogins = failedLogins;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public boolean isUnusualTimeAccess() {
        return unusualTimeAccess;
    }

    public void setUnusualTimeAccess(boolean unusualTimeAccess) {
        this.unusualTimeAccess = unusualTimeAccess;
    }

    public boolean isAttackDetected() {
        return attackDetected;
    }

    public void setAttackDetected(Boolean attackDetected) {
        this.attackDetected = attackDetected;
    }
}

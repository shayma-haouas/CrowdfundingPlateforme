package com.example.backend.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Boolean getAttackDetected() {
        return attackDetected;
    }

    public void setAttackDetected(Boolean attackDetected) {
        this.attackDetected = attackDetected;
    }

    @Column(name = "attack_detected", nullable = true)
    private Boolean  attackDetected;

    @ManyToOne
    @JoinColumn(name = "user_id")

    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setAttackDetected(boolean attackDetected) {
        this.attackDetected = attackDetected;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

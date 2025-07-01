package com.example.backend.Repository;



import com.example.backend.Entities.UserActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Long> {
    UserActivityLog findByUser_Id(Long userId);
}

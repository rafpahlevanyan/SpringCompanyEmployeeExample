package com.example.springcompanyemployeeexample.service;


import com.example.springcompanyemployeeexample.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSchedulingService {

    private final UserService userService;


//    @Scheduled(fixedDelay = 3000)
    //every day in 9:00 am
    @Scheduled(cron = "0 0 9 * * *")
    public void removeExpiredTokens() {

        List<User> all = userService.findAll();
        for (User user : all) {
            LocalDateTime tokenCreatedDate = user.getTokenCreatedDate();
            if (tokenCreatedDate != null) {
                LocalDateTime expireDate = tokenCreatedDate.plusDays(1);
                if (StringUtils.isNotEmpty(user.getToken()) && LocalDateTime.now().isAfter(expireDate)) {
                    user.setToken(null);
                    user.setTokenCreatedDate(null);
                    userService.save(user);
                }
            }

        }


    }

}

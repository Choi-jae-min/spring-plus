package org.example.expert.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        try {
            userBulkInsert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void userBulkInsert() {
        int totalCount = 5_000_000;
        int batchSize = 10_000;
        String encodedPassword = passwordEncoder.encode("12345678");

        for (int i = 0; i < totalCount / batchSize; i++) {
            List<Object[]> batch = new ArrayList<>();

            for (int j = 0; j < batchSize; j++) {
                int index = i * batchSize + j;
                String nickname = index + " : " + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
                batch.add(new Object[]{
                        index + "@test.com",
                        encodedPassword,
                        nickname,
                        "USER",
                        LocalDateTime.now(),
                        LocalDateTime.now()
                });
            }

            jdbcTemplate.batchUpdate(
                    "INSERT INTO users (email, password, nickname, user_role, created_at, modified_at) VALUES (?,?,?,?,?,?)",
                    batch
            );
        }
    }

}

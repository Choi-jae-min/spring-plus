package org.example.expert.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
class UserBulkInsertTest {

    // 최종 생성 유저의 수
    private static final int TOTAL_COUNT = 5_000_000;

    private static final int BATCH_SIZE = 10_000;

    private static final String DEFAULT_PASSWORD = "$2a$10$7EqJtq98hPqEX7fNZaFWoOHiVfG2sM4bxN8H4x0Q0M0Lr0JH01W9a";

    private static final String DEFAULT_ROLE = "USER";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void bulkInsertFiveMillionUsers() {
        long startedAt = System.currentTimeMillis();

        for (int start = 0; start < TOTAL_COUNT; start += BATCH_SIZE) {
            int currentBatchSize = Math.min(BATCH_SIZE, TOTAL_COUNT - start);
            int batchStart = start;

            jdbcTemplate.batchUpdate(
                    """
                    INSERT INTO users (email, password, nickname, user_role, created_at, modified_at)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int index) throws SQLException {
                            long sequence = (long) batchStart + index + 1;
                            Timestamp now = Timestamp.valueOf(LocalDateTime.now());

                            ps.setString(1, "bulk_user_" + sequence + "@example.com");
                            ps.setString(2, DEFAULT_PASSWORD);
                            ps.setString(3, generateNickname(sequence));
                            ps.setString(4, DEFAULT_ROLE);
                            ps.setTimestamp(5, now);
                            ps.setTimestamp(6, now);
                        }

                        @Override
                        public int getBatchSize() {
                            return currentBatchSize;
                        }
                    }
            );

            int inserted = batchStart + currentBatchSize;
            System.out.println("insert 진행: " + inserted + "/" + TOTAL_COUNT);
        }

        long elapsedMillis = System.currentTimeMillis() - startedAt;
        System.out.println("bulk insert 완료, 총 소요 시간: " + elapsedMillis + " ms");
    }

    private String generateNickname(long sequence) {
        String randomPrefix = Long.toString(
                ThreadLocalRandom.current().nextLong(36L * 36L * 36L * 36L), 36
        );
        return "user_" + padLeft(randomPrefix, 4) + "_" + Long.toString(sequence, 36);
    }

    private String padLeft(String value, int targetLength) {
        if (value.length() >= targetLength) {
            return value;
        }
        StringBuilder builder = new StringBuilder(targetLength);
        for (int i = value.length(); i < targetLength; i++) {
            builder.append('0');
        }
        builder.append(value);
        return builder.toString();
    }
}
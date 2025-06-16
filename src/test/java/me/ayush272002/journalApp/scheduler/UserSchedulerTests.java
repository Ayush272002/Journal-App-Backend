package me.ayush272002.journalApp.scheduler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled
class UserSchedulerTests {

    @Autowired
    private UserScheduler userScheduler;

    @Test
    @Disabled
    void testFetchUsersAndSendSaMail() {
        userScheduler.fetchUsersAndSendSaMail();
    }
}

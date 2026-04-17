package com.suncontrol.common.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppStartRunner implements ApplicationRunner {
    private final GenerationScheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        scheduler.init();
    }
}

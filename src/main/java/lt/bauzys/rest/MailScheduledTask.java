package lt.bauzys.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MailScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(MailScheduledTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 2000)
    public void logTile() {
        log.info("System time from MailScheduledTask {}", dateFormat.format(new Date()));
    }
}

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
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final QueuedMailService mailService;

    public MailScheduledTask(QueuedMailService mailService) {
        this.mailService = mailService;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60) // run once per hour at the fixed rate.
    public void logTile() {
        log.info("Mailing from mailing queue started at {}", dateFormat.format(new Date()));
        mailService.mailFromQueue();
    }
}

package lt.bauzys.rest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;

@SpringBootTest
@AutoConfigureMockMvc
public class MailServiceTest {
    private static Logger logger = LoggerFactory.getLogger(MailServiceTest.class);

    @Autowired
    private QueuedMailService mailService;

    @Autowired
    private Helpers helpers;

    @Test
    public void sendEmailsFromQueue() throws Exception{
        // add 2 mail to queue
        helpers.addToMailQueue("m1@mail.com", "subject 1", "message content 1");
        helpers.addToMailQueue("m1@mail", "subject 1", "message content 1");

        // send all mails from queue

        mailService.mailFromQueue();

        // zero unprocessed mails should be in the queue


    }

    @Test
    public void sendOneMailDirectlyByService() {
        try {
            mailService.sendMail("bauzys@gmail.com", "test subject", "test message");
        } catch (MailException me) {
            logger.error(me.getMessage());
        }
    }
}

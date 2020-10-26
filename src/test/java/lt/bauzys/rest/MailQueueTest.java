package lt.bauzys.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MailQueueTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Helpers helpers;

    @Test
    public void testMailAddToQueue() throws Exception {
        helpers.assertMailListHasMails(0);

        helpers.addToMailQueue("m1@mail.com", "subject 1", "message content 1");
        helpers.addToMailQueue("m1@mail", "subject 1", "message content 1");

        helpers.assertMailListHasMails(2);
    }

    @Test
    public void testMailGetFromQueue() throws Exception {
        helpers.mailsRemoveAll();
        helpers.assertMailListHasMails(0);

        helpers.addToMailQueue("m1@mail.com", "subject 1", "message content 1");
        helpers.addToMailQueue("m1@mail", "subject 1", "message content 1");

        List<QueuedMail> mailList = helpers.getListOfMails();
        assertEquals(2, mailList.size());

        for (QueuedMail queuedMail : mailList) {
            QueuedMail mail = getQueuedMail(queuedMail.getId());
            assertNotNull(mail);
            assertEquals(queuedMail.getId(), mail.getId());
        }

        helpers.assertMailListHasMails(2);
    }

    @Test
    public void sendOneMailFromQueue() {

    }

    private QueuedMail getQueuedMail(Long id) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/queue/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseString, QueuedMail.class);
    }
}

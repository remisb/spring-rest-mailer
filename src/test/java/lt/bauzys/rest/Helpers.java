package lt.bauzys.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class Helpers {
    private static Logger logger = LoggerFactory.getLogger(Helpers.class);

    @Autowired
    private MockMvc mockMvc;

    void addToMailQueue(String email, String subject, String message) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        QueuedMail map = new QueuedMail(email, subject, message);
        String jsonString = mapper.writeValueAsString(map);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/queue")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        String contentString = result.getResponse().getContentAsString();
        assertNotNull(result);
    }

    void assertMailListHasMails(int count) throws Exception {
        List<QueuedMail> list = getListOfMails();
        assertEquals(count, list.size());
    }

    void mailsRemoveAll() throws Exception {
        List<QueuedMail> mailList = getListOfMails();
        for (QueuedMail mail : mailList) {
            mailRemove(mail.getId());
        }
    }

    private String mailRemove(long id) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/queue/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        return result.getResponse().getContentAsString();
    }

    List<QueuedMail> getListOfMails() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/queue")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        QueuedMail[] a = new ObjectMapper()
                .readValue(response, QueuedMail[].class);
        return Arrays.asList(a.clone());
    }
}

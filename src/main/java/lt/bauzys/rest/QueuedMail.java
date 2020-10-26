package lt.bauzys.rest;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class QueuedMail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Email(message = "Email should be valid")
    @NotBlank
    private String email;

    @NotNull(message = "Subject cannot be null")
    @NotBlank
    private String subject;

    @NotNull(message = "Message cannot be null")
    @NotBlank
    private String message;
    private String failMessage;
    private Date sentAt;

    public QueuedMail() {}

    public QueuedMail(String email, String subject, String message) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setSendFailed(String faileMessage) {
        this.failMessage = faileMessage;
    }

    public void setSentAt(Date date) {
        this.sentAt = date;
    }
}

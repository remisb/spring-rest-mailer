package lt.bauzys.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.sql.Date;

@Service
public class QueuedMailServiceImpl implements QueuedMailService {
    private static final Logger log = LoggerFactory.getLogger(QueuedMailServiceImpl.class);

    private final MailSenderConfig mailSenderConfig;

    final QueuedMailRepository repository;
    final JavaMailSender mailSender;

    public QueuedMailServiceImpl(QueuedMailRepository repository, JavaMailSender mailSender, MailSenderConfig mailSenderConfig) {
        this.repository = repository;
        this.mailSender = mailSender;
        this.mailSenderConfig = mailSenderConfig;
    }

    @Override
    public List<QueuedMail> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<QueuedMail> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public QueuedMail save(QueuedMail queuedMail) {
        return repository.save(queuedMail);
    }

    @Override
    public void sendMail(String email, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSenderConfig.getMailerFrom());
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public String mailFromQueue() {
        log.info("Mailing fro queue started manually.");

        List<QueuedMail> queuedMails = findAll();
        queuedMails.forEach(queuedMail -> {
            try {
                sendMail(queuedMail.getEmail(), queuedMail.getSubject(), queuedMail.getMessage());
                queuedMail.setSentAt(new Date(System.currentTimeMillis()));
            } catch (MailException me) {
                queuedMail.setSendFailed(me.getMessage());
                repository.save(queuedMail);
            }
        });
        return "Mailing fro queue started manually.";
    }
}

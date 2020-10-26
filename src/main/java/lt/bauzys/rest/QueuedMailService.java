package lt.bauzys.rest;

import java.util.List;
import java.util.Optional;

public interface QueuedMailService {
    List<QueuedMail> findAll();
    Optional<QueuedMail> findById(final Long id);
    void deleteById(final Long id);
    QueuedMail save(QueuedMail queuedMail);
    void sendMail(String email, String subject, String message);
    String mailFromQueue();
}

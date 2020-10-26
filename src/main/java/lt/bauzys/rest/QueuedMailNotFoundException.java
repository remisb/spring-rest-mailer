package lt.bauzys.rest;

public class QueuedMailNotFoundException extends RuntimeException {

    QueuedMailNotFoundException(Long id) {
        super("Could not find queued mail " + id);
    }
}
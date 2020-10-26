package lt.bauzys.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/queue")
public class QueuedMailController {

    private final QueuedMailService service;

    public QueuedMailController(QueuedMailService service) {
        this.service = service;
    }

    @GetMapping
    public List<QueuedMail> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addMail(@Valid @RequestBody QueuedMail mail) {
        service.save(mail);
        return ResponseEntity.ok("Mail was successfully added to the queue.");
    }

    @GetMapping("/{id}")
    public QueuedMail getById(@PathVariable("id") Long id) {
        return service.findById(id).orElseThrow(() -> new QueuedMailNotFoundException(id));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @PostMapping("/doMailing")
    public ResponseEntity<String> mailFromQueue() {
        service.mailFromQueue();
        return ResponseEntity.ok("Mailing from queue started manually.");
    }
}

package lt.bauzys.rest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueuedMailRepository extends JpaRepository<QueuedMail, Long> {}

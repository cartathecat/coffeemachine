package app.com.coffeemachine.repository;

import app.com.coffeemachine.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnOffRepository extends JpaRepository<Status, Long> {
}

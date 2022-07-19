package app.com.coffeemachine.repository;

import app.com.coffeemachine.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnOffRepository extends JpaRepository<Status, Long> {
}

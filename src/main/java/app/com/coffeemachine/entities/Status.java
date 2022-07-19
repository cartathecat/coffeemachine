package app.com.coffeemachine.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="onoff")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    @Column(name="localdatetime")
    private LocalDateTime localDateTime;
    @Column(name="status")
    private String status;

    public Status() {
        this.localDateTime = LocalDateTime.now();
    }

    public Status(String status) {
        this.localDateTime = LocalDateTime.now();
        this.status = status;
    }

    public long getId() {
        return this.Id;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Status{" +
                "Id=" + this.Id +
                ", localDateTime=" + this.localDateTime +
                ", status='" + this.status + '\'' +
                '}';
    }
}

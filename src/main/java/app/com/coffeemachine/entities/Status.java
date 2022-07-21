package app.com.coffeemachine.entities;

//import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Status {

    private long Id;
    private LocalDateTime localDateTime;
    private String status;

    public Status(long id, LocalDateTime d, String status) {
        this.Id = id;
        this.localDateTime = d;
        this.status = status;
    }

    public Status(String status) {
        this.localDateTime = LocalDateTime.now();
        this.status = status;
    }

    public Status() {
        this.localDateTime = LocalDateTime.now();
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

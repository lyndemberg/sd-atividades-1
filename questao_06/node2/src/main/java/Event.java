import java.io.Serializable;

public class Event implements Serializable {
    private String arquivo;
    private String eventType;

    public Event(){

    }

    public Event(String arquivo, String eventType) {
        this.arquivo = arquivo;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Event{" +
                "arquivo='" + arquivo + '\'' +
                ", eventType='" + eventType + '\'' +
                '}';
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}

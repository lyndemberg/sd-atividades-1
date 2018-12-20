import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.WatchEvent;

public class CheckService {
    private final Socket observer;
    private final String diretorio;
    private Monitor monitor;
    private Thread threadMonitor;

    public CheckService(Socket observer, String diretorio) {
        this.observer = observer;
        this.diretorio = diretorio;
    }

    public void start(){
        Monitor monitor = new Monitor(diretorio, this);
        this.threadMonitor = new Thread(monitor);
        this.threadMonitor.start();
    }

    public void notify(WatchEvent event){
        try {
            ObjectOutputStream out = new ObjectOutputStream(observer.getOutputStream());
            out.writeObject(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "CheckService{" +
                "observer=" + observer +
                ", diretorio='" + diretorio + '\'' +
                ", monitor=" + monitor +
                ", threadMonitor=" + threadMonitor +
                '}';
    }
}

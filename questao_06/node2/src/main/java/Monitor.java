import java.io.IOException;
import java.nio.file.*;

public class Monitor implements Runnable{
        private final String diretorio;
        private final CheckService checkService;

        public Monitor(String diretorio, CheckService checkService){
            this.diretorio = diretorio;
            this.checkService = checkService;
        }

        @Override
        public void run() {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                Paths.get(diretorio).register(watchService,
                        StandardWatchEventKinds.ENTRY_MODIFY,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_CREATE);
                while(true){
                    WatchKey key = watchService.take();
                    for(WatchEvent<?> e : key.pollEvents()){
                        Event event = new Event(e.context().toString(), e.kind().name());
                        this.checkService.notify(event);
                    }
                    key.reset();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getDiretorio() {
            return diretorio;
        }
}

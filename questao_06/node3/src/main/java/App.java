import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        File dir = new File("../tmp"    );
        if(dir.exists()){
            //CRIAR UM ARQUIVO DE TESTE PARA GERAR NOTIFICAÇÃO
            File file1 = new File("../tmp/teste.txt");
            file1.createNewFile();
        }
    }
}

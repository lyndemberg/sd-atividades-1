import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class App {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost",19000));
        while(true){
            Socket socket = server.accept();
            byte[] data = new byte[8];
            socket.getInputStream().read(data);
            int[] numeros = convertByteArrayToIntArray(data);
            byte[] resultado = new byte[8];
            if(numeros[0] != numeros[1]){
                Socket socketToNode3 = new Socket("localhost",20000);
                socketToNode3.getOutputStream().write(convertIntArrayToByteArray(numeros));
                socketToNode3.getInputStream().read(resultado);
                socketToNode3.close();
            }else{
                double valor = 0;
                resultado = doubleToByteArray(valor);
            }
            socket.getOutputStream().write(resultado);
            socket.close();
        }
//        server.close();
    }

    private static byte[] doubleToByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    private static int[] convertByteArrayToIntArray(byte[] data) {
        if (data == null || data.length % 4 != 0) return null;
        // ----------
        int[] ints = new int[data.length / 4];
        for (int i = 0; i < ints.length; i++)
            ints[i] = ( convertByteArrayToInt(new byte[] {
                    data[(i*4)],
                    data[(i*4)+1],
                    data[(i*4)+2],
                    data[(i*4)+3],
            } ));
        return ints;
    }
    private static byte[] convertIntArrayToByteArray(int[] data) {
        if (data == null) return null;
        // ----------
        byte[] byts = new byte[data.length * 4];
        for (int i = 0; i < data.length; i++)
            System.arraycopy(convertIntToByteArray(data[i]), 0, byts, i * 4, 4);
        return byts;
    }

    private static byte[] convertIntToByteArray( final int i ) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
    }

    private static int convertByteArrayToInt(byte[] data) {
        if (data == null || data.length != 4) return 0x0;
        // ----------
        return (int)( // NOTE: type cast not necessary for int
                (0xff & data[0]) << 24  |
                        (0xff & data[1]) << 16  |
                        (0xff & data[2]) << 8   |
                        (0xff & data[3]) << 0
        );
    }
}

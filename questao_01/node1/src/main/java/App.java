import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Random;

public class App {
    public static void main(String[] args) throws IOException {
        Random random = new Random();
        int numero1 = random.nextInt(101);
        int numero2 = random.nextInt(101);
        System.out.println(numero1);
        System.out.println(numero2);
        int[] numeros = {numero1,numero2};
        byte[] resultadoBytes = new byte[8];
        Socket socket = new Socket("localhost",20000);
        socket.getOutputStream().write(convertIntArrayToByteArray(numeros));
        socket.getInputStream().read(resultadoBytes);
        double resultado = toDouble(resultadoBytes);
        System.out.println("O resultado da equação é ---> "+resultado);
        socket.close();
    }

    private static byte[] convertIntArrayToByteArray(int[] data) {
        if (data == null) return null;
        // ----------
        byte[] byts = new byte[data.length * 4];
        for (int i = 0; i < data.length; i++)
            System.arraycopy(convertIntToByteArray(data[i]), 0, byts, i * 4, 4);
        return byts;
    }
    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }

    private static byte[] convertIntToByteArray( final int i ) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
    }
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by whitelife on 2016. 7. 22..
 */
public class SampleHttpServer {

    private final static int PORT = 5000;

    public static void main(String[] args) {

        try {

            ServerSocket server = new ServerSocket(PORT);
            System.out.println("SampleHttpServer start...");

            while (true) {
                Socket client = server.accept();
                System.out.println(client);

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream());

                String line;
                int bodyLen = -1;

                while((line = in.readLine()) != null && (line.length() != 0)) {
                    System.out.println(line);

                    if (line.indexOf("Content-Length:") > -1) {
                        bodyLen = Integer.parseInt(line.substring(line.indexOf("Content-Length:") + 16, line.length()));
                    }
                }

                if (bodyLen > 0) {
                    char[] charBodyArray = new char[bodyLen];
                    in.read(charBodyArray, 0, bodyLen);
                    System.out.println(String.valueOf(charBodyArray));
                }

                out.println("okay...");
                out.flush();

                out.close();
                in.close();

                client.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

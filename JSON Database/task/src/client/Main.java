package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34522;
    final ClientArgs clientArgs = new ClientArgs();

    public static void main(String[] args) {
        Main main = new Main();
        main.argsHandler(args);  // argsHandler is an instance method, therefore main is instantiated
        String type = main.clientArgs.getType() != null? main.clientArgs.getType() : "";
        int index = main.clientArgs.getIndex();
        String message = main.clientArgs.getMessage() != null? main.clientArgs.getMessage() : "";

        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            // Requirement #1: Print client started after it is connected to server
            System.out.println("Client started!");

            // Requirement #2: Send request to server
            String msg;
            if (type.equals("exit")) {
                msg = type;
            } else if (type.equals("set")){
                msg = type + " " + index + " " + message;
            } else {
                msg = type + " " + index;
            }
            output.writeUTF(msg);
            System.out.println("Sent: " + msg);

            // Requirement #3: Receive feedback from server
            String receivedMsg = input.readUTF();
            System.out.println("Received: " + receivedMsg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        void argsHandler(String[] args) {
        JCommander jCommander = new JCommander(clientArgs);

        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            showUsage(jCommander);
        }

        if (clientArgs.isHelp()) {
            showUsage(jCommander);
        }
    }

    void showUsage(JCommander jCommander) {
        jCommander.usage();
        System.exit(0);
    }


}

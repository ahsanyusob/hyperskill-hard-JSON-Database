package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORT = 34522;

    public static void main(String[] args) {
        boolean runServer = true;
        Database database100 = new Database(100);

        try (ServerSocket server = new ServerSocket(PORT)) {
            // Requirement #1: Print "Server started!" before accepting any client
            System.out.println("Server started!");

            while (runServer) {
                try (
                        Socket socket = server.accept(); // accepting a new client
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    // Requirement #2: Read and interpret the message from client
                    String receivedMsg = input.readUTF(); // reading a message
//                    System.out.println("Received: " + receivedMsg);
                    String processedMsg = processMsg(receivedMsg, database100);

                    // Requirement #3: Send feedback to client
                    if (processedMsg.equals("EXIT")) {
                        output.writeUTF("OK");
                        runServer = false;
                    } else {
                        output.writeUTF(processedMsg); // resend it to the client
//                        System.out.println("Sent: " + processedMsg);
                    }

                }
            }

        } catch (IOException e) { e.printStackTrace(); }

    }

    private static String processMsg(String message, Database database) {
        String[] input = message.split(" ", 3);
        String input1 = input[0].trim().toLowerCase();
        String input2 = input.length > 1 ? input[1].trim() : "";
        String input3 = input.length == 3 ? input[2].trim() : "";
        int number = 0;
        try { number = Integer.parseInt(input2);
        } catch (NumberFormatException ignored) { // nothing to do here, int number remains zero and leads to error
        }

        switch (input1) {
            case "set":
                if (number > 0 && number <= 100) {
                    database.set(number - 1, input3);
                    return "OK";
                } else {
                    return "ERROR";
                }
            case "get":
                if (number > 0 && number <= 100 && !database.get(number - 1).equals("")) {
                    return database.get(number - 1);
                } else {
                    return "ERROR";
                }
            case "delete":
                if (number > 0 && number <= 100) {
                    database.set(number - 1, "");
                    return "OK";
                } else {
                    return "ERROR";
                }
            case "exit":
                return "EXIT";
            default:
                return "ERROR";
        }

    }
}

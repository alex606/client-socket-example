package com.example.ClientSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;


@RestController
public class Client {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper objectMapper;

    public Client() {
        String hostName = "192.168.0.150"; // or IP address of the server
        int portNumber = 12345; // the port number on which the server is listening
        this.objectMapper = new ObjectMapper();

        try {
            clientSocket = new Socket(hostName, portNumber);
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientResponseOnConnect = in.readLine();
            System.out.println(clientResponseOnConnect);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }

    @GetMapping("/getLibraryItems")
    public ResponseEntity<String> getLibraryItems() throws IOException, ClassNotFoundException {

        Message m = new Message(1, "BROWSE", "Sending message to server...", List.of());
        out.println(
                objectMapper.writeValueAsString(m)
        );
        String catalog = in.readLine();
        StringBuilder sb = generateResponse(catalog);
        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    @GetMapping("/checkout/{id}/{name}")
    public ResponseEntity<String> checkoutLibraryItem(
            @PathVariable int id,
            @PathVariable String name
    ) throws IOException {

        Message m = new Message(id, "CHECKOUT", name, List.of());
        out.println(
                objectMapper.writeValueAsString(m)
        );
        String catalog = in.readLine();
        StringBuilder sb = generateResponse(catalog);
        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    @GetMapping("/checkin/{id}/{name}")
    public ResponseEntity<String> checkInLibraryItem(
            @PathVariable int id,
            @PathVariable String name
    ) throws IOException, ClassNotFoundException {

        Message m = new Message(id, "CHECKIN", name, List.of());
        out.println(
                objectMapper.writeValueAsString(m)
        );
        String catalog = in.readLine();
        StringBuilder sb = generateResponse(catalog);
        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }


    private StringBuilder generateResponse(String message) throws JsonProcessingException {
        Message m2 = objectMapper.readValue(message, Message.class);
        StringBuilder sb = new StringBuilder();
        for (String s : m2.getCollection()) {
            LibraryItem item = objectMapper.readValue(s, LibraryItem.class);
            sb.append(item.getId() + ": ")
                    .append(item.getTitle())
                    .append("<br />")
                    .append(item.getAuthor())
                    .append("<br />")
                    .append(item.getItemType())
                    .append("<br />")
                    .append(item.isCheckedOut())
                    .append("<br />")
                    .append(item.getCheckedOutBy())
                    .append("<br />")
                    .append("<br />");
        }
        sb.append(m2.getErrorMessage());
        return sb;
    }
}

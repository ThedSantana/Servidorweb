/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;

/**
 *
 * @author Itzel
 */
import java.io.*;
import java.net.*;
import java.util.*;

class WebServer {

    public static void main(String argv[]) throws Exception {
        String requestMessageLine;
        String fileName;
        ServerSocket listenSocket = new ServerSocket(80);
        Socket connectionSocket = listenSocket.accept();
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        requestMessageLine = inFromClient.readLine();
        StringTokenizer tokenizedLine = new StringTokenizer(requestMessageLine);
        if (tokenizedLine.nextToken().equals("GET")) {
            fileName = tokenizedLine.nextToken();
            if (fileName.startsWith("/") == true) {
                fileName = fileName.substring(1);
            }
            File file = new File("C:\\Users\\Itzel\\Desktop\\WebServer\\src\\webserver\\Letreros.html");
            int numOfBytes = (int) file.length();
            FileInputStream inFile = new FileInputStream("C:\\Users\\Itzel\\Desktop\\WebServer\\src\\webserver\\Letreros.html");
            byte[] fileInBytes = new byte[numOfBytes];
            inFile.read(fileInBytes);
            outToClient.writeBytes("HTTP/1.0 200 Document Follows\r\n");
            if (fileName.endsWith(".jpg")) {
                outToClient.writeBytes("Content-Type: image/jpeg\r \n");
            }
            if (fileName.endsWith(".gif")) {
                outToClient.writeBytes("Content-Type: image/gif\r \n");
            }
            outToClient.writeBytes("Content-Length: " + numOfBytes + "\r \n");
            outToClient.writeBytes("\r\n");
            outToClient.write(fileInBytes, 0, numOfBytes);
            connectionSocket.close();
        } else {
            System.out.println("Bad Request Message");
        }
    }
}

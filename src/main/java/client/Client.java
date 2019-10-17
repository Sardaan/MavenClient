package client;

import commands.Command;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class Client {

    private static String login;
    private static String password;
    private static String mail;
    private final int PORT;
    private final String ADDR;
    private SocketChannel socketChannel;

    Client(String host, int port){
        this.ADDR = host;
        this.PORT = port;

    }

    public static String getPassword() {
        return password;
    }

    public static String getLogin() {
        return login;
    }

    public static String getMail() {
        return mail;
    }

    public static void setPassword(String password) {
        Client.password = password;
    }

    public static void setLogin(String login) {
        Client.login = login;
    }

    public static void setMail(String mail) {
        Client.mail = mail;
    }

    String communicate(Command command){

        try{
            InetSocketAddress hostAddress = new InetSocketAddress(ADDR, PORT);
            socketChannel= SocketChannel.open(hostAddress);
            writeData(command);
            
            return readData();
        }catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lost server connection. Write ~yes~ to reconnect or ~no~ to end the program");
            String answer;
            while (!(answer= new Scanner(System.in).nextLine()).equals("no")) {
                if (answer.equals("yes")){
                    return communicate(command);
                }
            }
            System.exit(1);
        }
        return "";
    }

    private void writeData(Command command) throws IOException{
       
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(command);
        out.flush();
        socketChannel.write(ByteBuffer.wrap(bos.toByteArray()));

    }
    private String readData() throws IOException{

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1000000);
        long time = System.currentTimeMillis();
        int check = socketChannel.read(byteBuffer);
        if (check == -1)
            throw new IOException();
        while (check == 0){
            if (System.currentTimeMillis() - time > 2000){
                throw  new IOException();
            }
            check = socketChannel.read(byteBuffer);
        }

        byteBuffer.flip();
        byte[] bb = new byte[byteBuffer.remaining()];
        byteBuffer.get(bb);
            
        return new String(bb, StandardCharsets.UTF_8);
    }
    
}
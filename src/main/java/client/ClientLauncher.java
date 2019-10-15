package client;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientLauncher {
    
    
    public static void main(String[] args) {

        Client client = new Client("localhost", 3080);
        
        System.out.println("Welcome! Login or register if you don't have an account");
        while (true) {
            try {
                String request = readConsole();
                if (request.equals("exit")) {
                    break;
                }
                System.out.println(client.communicate(RequestHandler.getCommand(request)));

            }catch (NoSuchElementException e){
                System.out.println("You stopped!");
                System.exit(1);
            }

        }
    }
    
    static String readConsole(){
        Scanner sc = new Scanner(System.in);
        String request = sc.nextLine();

        if (request.contains("{")){
            String next;
            int open = 1;
            int close = 0;
            if (request.contains("}"))
                close++;
            while (open!=close) {
                next = sc.nextLine();
                if (next.contains("{")) {
                    open++;
                }
                if (next.contains("}}")) {
                    close+=2;
                }
                if (next.contains("}")) {
                    close++;
                }
                request = request + next;
            }
        }
        return request.trim();
    }

}

//todo рестарт, переподключение, если ввести бред

//todo remove{} add ответы
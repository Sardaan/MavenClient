package client;

import collection.Human;
import commands.Command;
import collection.JSONreader;

class RequestHandler {

    private static String login;
    private static String password;

    static Command getCommand(String request) {
        Command command = new Command();
        if (request != null) {
            if(request.equals("logout")){
                login=null;
                password=null;//awtrodaalq
            }
            
            String[] commandParts = request.split(" ", 2);

            String commandName = commandParts[0];
            command.setCommandName(commandName);
            String argument = commandParts.length > 1 ? commandParts[1] : null;


            if (argument !=null && (commandName.contains("add") || commandName.contains("remove"))) {
                Human human = JSONreader.getHumanFromJSON(argument);
                if(human!=null)
                    command.setHuman(human);
                else System.out.println("Human wrong format");
            }
            else if (commandName.equals("register")){
                
                System.out.println("login: ");
                login = ClientLauncher.readConsole();
                System.out.println("mail: ");
                String mail = request+" "+ ClientLauncher.readConsole().trim();
                
                command.setMail(mail);
                
            }
            else if (commandName.equals("login")){
                System.out.println("login: ");
                login = ClientLauncher.readConsole().trim();
                System.out.println("password: ");
                password = ClientLauncher.readConsole();
            }

            command.setLogin(login);
            command.setPassword(password);
        }
        return command;
    }
}

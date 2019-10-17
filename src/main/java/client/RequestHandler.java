package client;

import collection.Human;
import commands.Command;
import collection.JSONreader;

class RequestHandler {

    static Command getCommand(String request) {
        Command command = new Command();
        if (request != null) {
            if(request.equals("logout")){
                Client.setLogin("");
                Client.setPassword("");//awtrodaalq
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
                Client.setLogin(ClientLauncher.readConsole());
                System.out.println("mail: ");
                Client.setMail(ClientLauncher.readConsole());
                
                command.setMail(Client.getMail());
                
            }
            else if (commandName.equals("login")){
                System.out.println("login: ");
                Client.setLogin(ClientLauncher.readConsole());
                System.out.println("password: ");
                Client.setPassword(ClientLauncher.readConsole());
            }

            command.setLogin(Client.getLogin());
            command.setPassword(Client.getPassword());
        }
        return command;
    }
}

//todo show if empty

//todo if login, mail are null
package model;

import java.io.*;
import java.util.ArrayList;

public class BaseHandler {

    public static void saveClient(ArrayList<Client> clients){
        try{
            FileOutputStream fileOut = new FileOutputStream("client_base.tmp");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(clients);
            objectOut.close();
            System.out.println("Client saved to base");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Client> readClient(){
        try{
            FileInputStream fileIn = new FileInputStream("client_base.tmp");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Client> clients = (ArrayList<Client>) objectIn.readObject();
            return clients;
        }
        catch(ClassNotFoundException | IOException e){
            e.printStackTrace();
            return null;
        }
    }
}

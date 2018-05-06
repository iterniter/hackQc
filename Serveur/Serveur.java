package testServer;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Serveur {
    private static Socket socket;
    static String user = "";
    static String userPassWord = "";
    static String body= "";
    private static final int Port = 5000;
    static Map<String,String> myMap = new HashMap<String, String>();


    public static void main(String[] args) throws Exception {
        System.out.print("LANCEMENT DU SERVEUR\n");
        InetAddress ip= InetAddress.getByName("127.0.0.1");
        // pour des fin de test
        readFromFile();
        
  	  	try {

  		ip = InetAddress.getLocalHost();
  		System.out.println("Current IP address : " + ip.getHostAddress());

  	  	} catch (UnknownHostException e) {

  		e.printStackTrace();

  	  	}

  	  	
        System.out.println("En attente d'utilisateurs...\n\n");
        ServerSocket serversocket;
        serversocket = new ServerSocket();
        serversocket.setReuseAddress(true);
        serversocket.bind(new InetSocketAddress(ip, Port));

        socket = serversocket.accept();

        //catch from the client
        BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        boolean userChecked = false;
        
        	//lire la premiere donnee qui vient par le buffert du client
            user = inputBuffer.readLine();
        	//lire la deuxieme donnee qui vient par le buffert du client
            userPassWord = inputBuffer.readLine();
            
          while (!userChecked)
          {
            if(myMap.containsKey(user)) {
                if(myMap.containsValue(userPassWord)) {
                    userChecked = true;
                    System.out.println("user Trouve et connection reussie");
                    try {
                        	// block de code d'envoie
                            output.println("le compte :"+ user + " est connecte");
                            body= inputBuffer.readLine();
                            while(!body.equals("exit"))
                            {
                            switch (body) 
                            {
                            case "ab": 
                            	body = "January";
                            	output.println(body);
                                break;
                            case "bc":  
                            	body = "February";
                            	output.println(body);
                                break;
                            case "cd":  
                            	body = "March";
                            	output.println(body);
                            	break;
                            
                            default: 
                            	body = "Invalid month";
                            	output.println(body);
                                break;
                            }
                            body= inputBuffer.readLine();
                        }
                            output.println(body);
                    } finally {
                        serversocket.close();
                    }
                    
                    
                }
                else {
                    userChecked = false;
                    System.out.println("password pas valide connection echoue");

                }
            }
            else{
                myMap.put(user, userPassWord);
                writeFile(user, userPassWord);
                userChecked = false;
                System.out.println("Username pas valide connection echoue");
            }
          }

        //afficher a temps reel
        output.flush();
        System.out.println("after the flush");

    }

    //Methode pour ecrire dans un fichier
    private static void writeFile(String userName, String password) {
        // TODO Auto-generated method stub

        final File fichier = new File(System.getProperty("user.dir") + File.separator + "users.txt");

        try {
            // creation d'un writer (un Ã©crivain)
            final FileWriter writer = new FileWriter(fichier,true);
            try {
                writer.append(userName + " " + password);
                writer.write(System.getProperty( "line.separator" ));
            } finally {
                // quoiqu'il arrive, on ferme le fichier
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Impossible de creer le fichier");
        }

    }
    
    private static void readFromFile() {
        // TODO Auto-generated method stub
        File file = new File(System.getProperty("user.dir") + File.separator + "users.txt");
     
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));


            String line;
            String[] credentials;
            
            Scanner sc2 = null;
            String username = "";
            String password = "";
            
            int testClassement = 0 ;
            try {
                sc2 = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();  
            }
            while (sc2.hasNextLine()) {
                    Scanner s2 = new Scanner(sc2.nextLine());
                while (s2.hasNext()) {
                	
                    String s = s2.next();
                   //pour savoir si le string a en registrer sera le username ou la mot de passe
                    if(testClassement % 2 == 0)
                    {
                        //System.out.println(s + " userName: " + testClassement);
                        username = s;
                        
                    }else{
                       // System.out.println(s + "passWord: " + testClassement);
                        password = s;
                    }
                    testClassement++;
                }
                myMap.put(username, password);
            }
            System.out.println(myMap.keySet() + " " + myMap.values());
            br.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

 
}
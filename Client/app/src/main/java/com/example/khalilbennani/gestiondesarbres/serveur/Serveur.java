package com.example.khalilbennani.gestiondesarbres.serveur;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
    static Map<String,String> myMap = new HashMap<String, String>();


    public static void main(String[] args) throws Exception {
        System.out.print("LANCEMENT DU SERVEUR\n\n");
        String ip = checkIP();
        int port = checkPort();
        readFromFile();

        System.out.println("En attente d'utilisateurs...\n\n");
        ServerSocket serversocket;
        InetAddress locIP = InetAddress.getByName(ip);
        serversocket = new ServerSocket();
        serversocket.setReuseAddress(true);
        serversocket.bind(new InetSocketAddress(locIP, port));



        socket = serversocket.accept();
        System.out.format("Application de filtres d'image lancée sur  %s:%d%n\n", ip, port);

        //khalil catch from the client
        BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
//        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);



        String userName;
        String password;
        boolean userChecked = false;

        //lire la premiere donnee qui vient par le buffert du client
        userName = inputBuffer.readLine();
        //lire la deuxieme donnee qui vient par le buffert du client
        password = inputBuffer.readLine();

        if(myMap.containsKey(userName)) {
            if(myMap.containsValue(password)) {
                userChecked = true;
                System.out.println("user Trouvé et connection reussi");
                try {
                    while (true) {
                        new SobelFilter(serversocket.accept(),userName).start();
                    }
                } finally {
                    serversocket.close();
                }



            }
            else {
                userChecked = false;
                System.out.println("password pas valide connection echoué");

            }
        }
        else{
            myMap.put(userName, password);
            writeFile(userName, password);
            userChecked = false;
            System.out.println("Username pas valide connection echoué");


        }
        System.out.println("Fin des if");

        //afficher a temps reel


        output.flush();
        System.out.println("after the flush close");


    }




    private static void readFromFile() {
        // TODO Auto-generated method stub
        File file = new File(System.getProperty("user.dir") + File.separator + "users.txt");
        if(!file.exists()) {
            //**pas besoin de creer un fichier puisque on peut mettre un par defaut
           /* try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));


            String line;
            String[] credentials;
            // while ((line = br.readLine()) != null) {
            // process the line.
            //  credentials = line.split(" ");
            //  user = credentials[0];
            //  userPassWord = credentials[1];
            //  myMap.put(user, userPassWord);

            // }

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

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    //Methode pour ecrire dans un fichier
    private static void writeFile(String userName, String password) {
        // TODO Auto-generated method stub

        final File fichier = new File(System.getProperty("user.dir") + File.separator + "users.txt");

        try {
            // creation d'un writer (un écrivain)
            final FileWriter writer = new FileWriter(fichier,true);
            try {
                writer.append(userName + " " + password + "\n");
            } finally {
                // quoiqu'il arrive, on ferme le fichier
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Impossible de creer le fichier");
        }

    }

    //Methode pour verifier le port
    private static int checkPort() {
        //verification du port
        // Get the server address from a dialog box.
        int port = 0;

        boolean portBool = false;

        do{
            System.out.println("Entrez le port:");
            @SuppressWarnings("resource")
            Scanner reader = new Scanner(System.in);
            port = reader.nextInt();


            if(port >= 5000 && port <= 5050)
            {
                System.out.println("le port saisi est correct");
                portBool = true;


            }else{
                System.out.println("le port saisi n'est pas correct, veuillez ressayer");

            }

        }while(!portBool);

        return port;
    }

    //Methode pour verifier l'adresse ip
    private static String checkIP() {
        // TODO Auto-generated method stub
        boolean isIp = false;
        String serverAddress = "";


        do{
            System.out.println("Entrez l'adresse IP du serveur:");
            @SuppressWarnings("resource")
            Scanner reader = new Scanner(System.in);
            serverAddress = reader.next();

            isIp = IPAddressValidator(serverAddress);

            if(!isIp)
            {
                System.out.println("Veuillez verifier l adresse ip entrée");
            }else{
                System.out.println("Adresse Ip Correcte");
            }

        }while(!isIp);


        return serverAddress;

    }


//Methode appelee par la methode checkip pour verifier une adresse ip
//selon un pattern pre etablie

    public static boolean IPAddressValidator(String ip){

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        matcher = pattern.matcher(ip);

        return matcher.find();
    }



    /**
     * A private thread to handle capitalization requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
    private static class SobelFilter extends Thread {
        private Socket socket;
        private String username;
        public SobelFilter(Socket socket, String username) {
            System.out.println("Nouvelle connexion avec le client " + username + " au " + socket);
            this.socket = socket;
            this.username = username;
        }

        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */
        public void run() {
            String fileName = "";
            try {
                BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                fileName = inputBuffer.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {


                while (true) {

                    Thread.sleep(33);

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

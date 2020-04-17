package main.java;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;


/**
 * <b>Class that handles the manipulation of text documents</b>
 *
 * @author Sean Peters
 */
public class FileEditor {
    // Filepath's
    final String ACCOUNTS = References.ACCOUNTS.getFilepath();

    /**
     * Reads the file with the given filepath
     *
     * @param filePath
     * @throws IOException
     */
    public void ReadFile(String filePath) throws IOException {
        try (Scanner fr = new Scanner(new FileReader(filePath))) {
            System.out.println(fr.nextLine());
            System.out.println(fr.nextLine());
            while (fr.hasNextLine() != true)
                System.out.println(fr.nextLine());
        }
    }

    /**
     * Creates a User object based off the provided username and password.
     * With the provided information, the accounts text document is searched for the provided user
     * to confirm that the username and password match and exist.
     *
     * @param username the input username
     * @param password the input password
     * @return User object that stores the username, password and type of the confirmed account
     * @throws FileNotFoundException
     */
    public User userIdentifier(String username, String password) throws FileNotFoundException {
        User account = null;
        try (Scanner scan = new Scanner(new FileReader(ACCOUNTS))) {
            String user, name = null, pass = null, type = null;
            while (scan.hasNextLine()) {
                user = scan.nextLine();
                if (username != null && password != null) {
                    String[] output = user.split("-");
                    if (output != null) {
                        name = output[0];
                        pass = output[1];
                        type = output[2];
                    }
                    if (name.contentEquals(username)) {
                        if (pass.contentEquals(password)) {
                            account = new User(name, pass, type);
                            break;
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid Username and/or Password");
            //e.printStackTrace();
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }
        return account;
    }

    /**
     * Used to modify a text document by replacing the oldString with the newString.
     *
     * @param filePath  Filepath of the text document
     * @param oldString The String to be replaced
     * @param newString The String that replaces oldString
     */
    public void modifyFile(String filePath, String oldString, String newString) {
        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));

            String line = reader.readLine();

            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
            }
            String newContent = oldContent.replaceAll(oldString, newString);
            writer = new FileWriter(fileToBeModified);
            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds a new line to a text document with the newString
     *
     * @param filePath  Filepath of the text document
     * @param newString String to be added to the text document
     */
    public void addToFile(String filePath, String newString) {
        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));
            String line = reader.readLine();

            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }
            String newContent = oldContent + newString;

            writer = new FileWriter(fileToBeModified);

            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes all blank lines in a text document
     *
     * @param filePath Filepath of the text document
     */
    public void clearEmptyLines(String filePath) {
        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));
            String line = reader.readLine();
            while (line != null) {
                if (line.length() != 0) {
                    oldContent = oldContent + line + System.lineSeparator();
                }
                line = reader.readLine();
            }
            String newContent = oldContent;
            writer = new FileWriter(fileToBeModified);
            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Scans through the inventory text document and converts all lines into Stock objects
     *
     * @return a Stock ObservableList which reflects the inventory text document
     * @throws FileNotFoundException
     */
    public ObservableList<Stock> getInventory() throws FileNotFoundException {
        ObservableList<Stock> inventory = FXCollections.observableArrayList();
        Scanner scan = new Scanner(new FileReader(References.INVENTORY.getFilepath()));
        while (scan.hasNext()) {
            String stock = scan.next();
            String[] output = stock.split("-");
            String ID = output[0];
            String Metal = output[1];
            String Type = output[2];
            String Dimensions = output[3];
            String Quantity = output[4];
            double Price = Double.parseDouble(output[5]);
            Stock item = new Stock(ID, Metal, Type, Dimensions, Quantity, Price);
            inventory.add(item);
        }
        scan.close();
        return inventory;
    }

    /**
     * Searches through the inventory text document to find the stock of the given ID
     *
     * @param id The ID of the Stock
     * @return a Stock item of the given ID
     * @throws FileNotFoundException
     */
    public Stock getStock(String id) throws FileNotFoundException {
        Stock item = null;
        Scanner scan = new Scanner(new FileReader(References.INVENTORY.getFilepath()));
        while (scan.hasNext()) {
            String stock = scan.next();

            String[] output = stock.split("-");
            String ID = output[0];
            if (ID.contentEquals(id)) {
                String Metal = output[1];
                String Type = output[2];
                String Dimensions = output[3];
                String Quantity = output[4];
                double Price = Double.parseDouble(output[5]);
                item = new Stock(ID, Metal, Type, Dimensions, Quantity, Price);
                break;
            }
        }
        scan.close();
        return item;
    }
}

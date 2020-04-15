package main.java;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;


public class FileEditor {
    // Filepath's
    String accounts = References.ACCOUNTS.getFilepath();

    /**
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


    public User userIdentifier(String username, String password) throws FileNotFoundException {
        User account = null;
        try (Scanner scan = new Scanner(new FileReader(accounts))) {
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

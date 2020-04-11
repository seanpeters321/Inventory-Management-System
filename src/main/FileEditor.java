package main;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;

public class FileEditor {
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

    /**
     * Checks if user
     * @return
     * @throws FileNotFoundException
     */
    public boolean isAdmin(String enteredUser, String enteredPass) throws FileNotFoundException{
        boolean admin = false;
        try (Scanner fr = new Scanner(new FileReader("./src/resources/txt/accounts.txt"))) {
            String userpass, pass = "", type = "";
            while (fr.hasNextLine()) {

                userpass = fr.nextLine();
                String[] output = userpass.split("-");
                String user = output[0];
                if(user.contentEquals(enteredUser)) {
                    pass = output[1];
                    type = output[2];
                    break;
                }
            }
            fr.close();

            if (pass.contentEquals(enteredPass) && type.contentEquals("admin"))
                return true;
        }
        return admin;
    }

    public boolean isEmployee(String enteredUser, String enteredPass) throws FileNotFoundException{
        boolean employee = false;
        try (Scanner fr = new Scanner(new FileReader("./src/resources/txt/accounts.txt"))) {
            String userpass, pass = "", type = "";
            while (fr.hasNextLine()) {

                userpass = fr.nextLine();
                String[] output = userpass.split("-");
                String user = output[0];
                if(user.contentEquals(enteredUser)) {
                    pass = output[1];
                    type = output[2];
                    break;
                }
            }
            fr.close();

            if (pass.contentEquals(enteredPass) && type.contentEquals("employee"))
                    return true;
        }
        return employee;
    }

    public void modifyFile(String filePath, String oldString, String newString){
        File fileToBeModified = new File(filePath);

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try{
            reader = new BufferedReader(new FileReader(fileToBeModified));

            String line = reader.readLine();

            while (line != null)
            {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }

            String newContent = oldContent.replaceAll(oldString, newString);

            writer = new FileWriter(fileToBeModified);

            writer.write(newContent);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();

                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void addToFile(String filePath, String newString){
        File fileToBeModified = new File(filePath);

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try{
            reader = new BufferedReader(new FileReader(fileToBeModified));

            String line = reader.readLine();

            while (line != null)
            {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }


            String newContent = oldContent + newString;

            writer = new FileWriter(fileToBeModified);

            writer.write(newContent);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();

                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}

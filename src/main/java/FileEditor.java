package main.java;


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
        try (Scanner scan = new Scanner(new FileReader(accounts))) {

            String user, name = null, pass = null, type = null;
            while (scan.hasNextLine()) {
                user = scan.nextLine();
                String[] output = user.split("-");
                name = output[0];
                pass = output[1];
                type = output[2];
                if (name.contentEquals(username)) {
                    break;
                }
            }
            User account = new User(name, pass, type);
            return account;
        }
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
}

package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
     * Searches through the accounts.txt document to match the entered username with
     * one existing in this system. If such a username exists the entered password
     * will be compared with the line in the document following the username, which
     * is the related password.
     *
     * @param enteredUser Username entered
     * @param enteredPass Password entered
     * @return If Username and Password match
     * @throws FileNotFoundException
     */
    public boolean userpassMatch(String enteredUser, String enteredPass) throws FileNotFoundException {
        boolean match = false;
        try (Scanner fr = new Scanner(new FileReader("./src/resources/txt/accounts.txt"))) {
            String userpass, pass = "";
            while (fr.hasNextLine()) {
                userpass = fr.nextLine();
                String[] output = userpass.split("-");
                String user = output[0];
                pass = output[1];
            }

            if (pass.contentEquals(enteredPass))
                return true;
            fr.close();
        }
        return match;
    }
}

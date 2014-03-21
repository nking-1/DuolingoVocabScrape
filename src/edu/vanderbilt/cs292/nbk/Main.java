package edu.vanderbilt.cs292.nbk;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your Duolingo username: ");
        String user = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        DuolingoFetcher fetch = new DuolingoFetcher(user, password);
        List<WordData> words = fetch.getWordData();
        
        for (WordData word : words) {
            System.out.println(word);
        }
    }


}

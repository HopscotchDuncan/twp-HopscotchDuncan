package edu.bsu.cs222.WikipediaProjectJavaFX;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WikipediaPageEditPeeker{

    Scanner util = new Scanner(System.in);

    public String formList(List<String> users, List<String> dates){
        StringBuilder formattedUsersAndDates = new StringBuilder();
        for(int i = 0; i< users.size(); i++){
            formattedUsersAndDates.append(dates.get(i));
            formattedUsersAndDates.append(" ");
            formattedUsersAndDates.append(users.get(i));
            formattedUsersAndDates.append("\n");
        }
        return formattedUsersAndDates.toString();
    }

    public void accessWikipediaEditors(){
        WikipediaFileParser fileParser = new WikipediaFileParser();
        System.out.println("Enter title of Wikipedia article:");
        String title = util.nextLine();
        if(title.equals("")){
            System.err.println("No title entered into field");
            System.exit(1);
        }
        ArrayList<String> users = fileParser.lastUsersWhoEdited(fileParser.getJSONFromURL(title));
        ArrayList<String> dates = fileParser.dateOfEdit(fileParser.getJSONFromURL(title));
        if(users == null){
            System.err.println("Page not found");
            System.exit(2);
        }
        if (fileParser.isRedirected()){
            System.out.println("Page was redirected");
        }
        System.out.println(formList(users, dates));
    }

}
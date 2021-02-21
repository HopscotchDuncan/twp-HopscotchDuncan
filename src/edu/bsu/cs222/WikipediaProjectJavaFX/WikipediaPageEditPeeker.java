package edu.bsu.cs222.WikipediaProjectJavaFX;

import java.util.List;

public class WikipediaPageEditPeeker{

    public String formList(List<String> users, List<String> dates){
        StringBuilder formattedUsersAndDates = new StringBuilder();
        for(int i = 0; i< users.size(); i++){
            formattedUsersAndDates.append(dates.get(i) + " " + users.get(i) + "\n");
        }
        return formattedUsersAndDates.toString();
    }

}
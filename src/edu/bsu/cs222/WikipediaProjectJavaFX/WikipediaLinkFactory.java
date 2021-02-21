package edu.bsu.cs222.WikipediaProjectJavaFX;

public class WikipediaLinkFactory {

    //turns user input into usable string for URL
    public String formatUserInput(String userInput) {
        String formattedInput;
        formattedInput = userInput.replace(" ", "%20");
        return formattedInput;
    }

    //general format is https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=Donald%20Trump&rvprop=timestamp|user&rvlimit=31&redirects
    public String buildLink(String title){
        String finalLink = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=";
        finalLink += title;
        finalLink += "&rvprop=timestamp|user&rvlimit=31&redirects";
        return finalLink;
    }
}
package edu.bsu.cs222.WikipediaProjectJavaFX;


import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WikipediaFileParser {

    private final WikipediaLinkFactory linkBuilder = new WikipediaLinkFactory();
    private Boolean redirect = false;
    private final StringBuilder data = new StringBuilder();

    public String getJSONFromURL(String userInput){
        try {
            String pageTitle = linkBuilder.formatUserInput(userInput);
            String jsonURL = linkBuilder.buildLink(pageTitle);
            URL url = new URL(jsonURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader bR = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String xml;
            while((xml = bR.readLine())!=null){
                data.append(xml);
            }
            String jsonData = data.toString();
            data.delete(0,data.length());
            if(jsonData.contains("redirects")){
                redirect = true;
            }
            return jsonData;
        }catch(MalformedURLException e){
            System.err.println("No Connection");
            System.exit(3);
        } catch (IOException e) {
            System.err.println("Check Connection");
            System.exit(3);
        }
        return null;
    }
    public String getJSONFromURLGUI(String userInput){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("A connection error has occurred");
        try {
            String pageTitle = linkBuilder.formatUserInput(userInput);
            String jsonURL = linkBuilder.buildLink(pageTitle);
            URL url = new URL(jsonURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader bR = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String xml;
            while((xml = bR.readLine())!=null){
                data.append(xml);
            }
            String jsonData = data.toString();
            data.delete(0,data.length());
            if(jsonData.contains("redirects")){
                redirect = true;
            }
            return jsonData;
        }catch (IOException e) {
            alert.showAndWait();
        }
        return null;
    }

    public boolean isRedirected(){
        return redirect;
    }
    public void setRedirect(boolean newValue){
        redirect = newValue;
    }

    public ArrayList<String> lastUsersWhoEdited(String jsonString) {
        ArrayList<String> users = new ArrayList<>();
        String[] dividedValues = jsonString.split("\"");
        for (int i = 0; i < dividedValues.length - 1; i++) {
            if (dividedValues[i].contains("user")) {
                //will fill array up to 30, else will just put the first one into another array and return that
                if (users.size() < 30) {
                    users.add(dividedValues[i + 2]);
                } else {
                    ArrayList<String> one = new ArrayList<>();
                    one.add(users.get(0));
                    return one;
                }
            }
        }
        return null;
    }

    public ArrayList<String> dateOfEdit(String jsonString){
            ArrayList<String> dates = new ArrayList<>();
            String[] dividedValues = jsonString.split("\"");
            for (int i = 0; i < dividedValues.length - 1; i++) {
                if (dividedValues[i].contains("timestamp")) {
                    //will fill array up to 30, else will just put the first one into another array and return that
                    if (dates.size() < 30) {
                        dates.add(dividedValues[i + 2]);
                    } else {
                        ArrayList<String> one = new ArrayList<>();
                        one.add(dates.get(0));
                        return one;
                    }
                }
            }
        return null;
        }
}
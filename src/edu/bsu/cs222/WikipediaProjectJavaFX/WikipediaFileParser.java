package edu.bsu.cs222.WikipediaProjectJavaFX;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

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

    public String getJSONfromURL(String userInput){
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
            if(jsonData.contains("redirects")){
                redirect = true;
            }
            return jsonData;
        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        return null;
    }

    public boolean isRedirected(){
        return redirect;
    }

    public ArrayList<String> lastUsersWhoEdited(String jsonString){
        ReadContext ctx = JsonPath.parse(jsonString);
        ArrayList<String> users = ctx.read("$..user");
        if(users.size()==31){
            ArrayList<String> one = new ArrayList<>();
            one.add(users.get(0));
            return one;
        }
        if(users.size()>0){
            return users;
        }else{
            return null;
        }
    }

    public ArrayList<String> dateOfEdit(String jsonString){
        ReadContext ctx = JsonPath.parse(jsonString);
        ArrayList<String> dates = ctx.read("$..timestamp");
        if(dates.size()==31){
            ArrayList<String> one = new ArrayList<>();
            one.add(dates.get(0));
            return one;
        }
        if(dates.size()>0){
            return dates;
        }else{
            return null;
        }
    }
}
package com.example.javapracticejson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.scene.chart.XYChart;

import java.io.FileReader;

public class APIUtility {
    public static Website getWebsiteFromJson() {
        Website website = null;
        try(
                FileReader fileReader = new FileReader("sampleJson.json");
                JsonReader jsonReader = new JsonReader(fileReader);
                )
        {
            Gson gson = new Gson();
            website = gson.fromJson(jsonReader, Website.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return website;
    }

    /**
     * Filter for barChart to sort by the id - ADDS UP SAME IDS
     */
    public static XYChart.Series<String, Integer> filterById(String selectedId)
    {
        Website website = getWebsiteFromJson();
        User currentUser;

        XYChart.Series<String, Integer> userAmount = new XYChart.Series<>();

        for (int x = 0; x < website.getUsers().size(); x++)
        {
            currentUser = website.getUsers().get(x);
            String userName = currentUser.getFirstName();
            int userId = currentUser.getUserId();
            int usersAmount = 0;

            for (int y = 0; y < website.getUsers().size(); y++) {
                currentUser = website.getUsers().get(y);
                int tempUserId = currentUser.getUserId();
                if (userId == tempUserId) {
                    usersAmount++;
                }

            }
            userAmount.getData().add(new XYChart.Data<>(userName, usersAmount));
        }
        return userAmount;
    }
}

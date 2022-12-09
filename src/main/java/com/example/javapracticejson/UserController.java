package com.example.javapracticejson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class UserController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> phoneColumn;

    @FXML
    private ComboBox<String> searchBarChartComboBox;

    @FXML
    private TableView<User> tableView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label userAmountLabel;

    @FXML
    private TableColumn<User, Integer> userIdColumn;

    @FXML
    private TextField searchTextField;

    private Website website;

    private User user;

    @FXML
    void searchBarChart(ActionEvent event) {
        barChart.getData().clear();
        barChart.getData().addAll(APIUtility.filterById(searchBarChartComboBox.getValue()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        barChart.setLegendVisible(false);
        website = APIUtility.getWebsiteFromJson();
        titleLabel.setText(website.getWebsite());

        //Loop through the json into the table
        for (int x = 0; x < website.getUsers().size(); x++) {
            user = website.getUsers().get(x);

            //Populate table
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
            tableView.getItems().add(user);
        }
        countResults();


        //Make comboBox options
/*        Set<String> userIds = website.getUsers().stream()
                .map(user -> String.valueOf(user.getUserId()))
                        .collect(Collectors.toSet());
        searchBarChartComboBox.getItems().addAll(userIds);*/

        //Populate the comboBox
        searchBarChartComboBox.getItems().addAll((website.getUsers().stream()
                .map(user -> String.valueOf(user.getUserId())))
                .distinct()
                .sorted()
                .collect(Collectors.toList()));

    }

    /**
     * Search by firstname USES On Action
     */
    @FXML
    void searchName()
    {
        tableView.getItems().clear();
        for (int x = 0; x < website.getUsers().size(); x++)
        {
            user = website.getUsers().get(x);
            if (user.getFirstName().contains(searchTextField.getText()))
            {
                user = website.getUsers().get(x);
                //Populate table
                userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
                firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
                lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
                phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
                tableView.getItems().add(user);
                countResults();
            }
        }
    }

    @FXML
    void countResults() {
        //Sum how many results were returned
        int resultSum = (int) tableView.getItems().stream()
                .mapToInt(user -> user.getUserId())
                .count();

        userAmountLabel.setText("Sum: " + String.valueOf(resultSum));
    }
}

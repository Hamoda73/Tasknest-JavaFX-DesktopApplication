package com.tasknest.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import java.util.List;
import com.tasknest.models.complaint;
import com.tasknest.services.ComplaintService;

public class statsController {

    @FXML
    private PieChart complaintPieChart;

    private ComplaintService complaintService;

    @FXML
    public void initialize() {
        complaintService = new ComplaintService();
        loadPieChartData();
    }

    private void loadPieChartData() {
        // Get the count of complaints for each type
        List<complaint> complaints = complaintService.afficher();
        int GeneralinquiryCount = 0;
        int BillingissueCount = 0;
        int CustomersupportCount = 0;
        for (complaint complaint : complaints) {
            String type = complaint.getType();
            if (type.equals("General inquiry")) {
                GeneralinquiryCount++;
            } else if (type.equals("Billing issue")) {
                BillingissueCount++;
            } else if (type.equals("Customer support")) {
                CustomersupportCount++;
            }
        }

        // Prepare data for the pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("General inquiry", GeneralinquiryCount),
                new PieChart.Data("Billing issue", BillingissueCount),
                new PieChart.Data("Customer support", CustomersupportCount)
        );

        // Set the data to the pie chart
        complaintPieChart.setData(pieChartData);
    }
}

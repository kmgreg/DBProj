package sample;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData; 
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.layout.GridPane;
import javafx.geometry.*;
import javafx.application.*;
public class CTController {
	@FXML 
	private Button Ba;
	
	@FXML
	private Button Add;
	private Main model;
	
	
	
	public CTController(Main mod) {
		model = mod;
		
	}
	
	@FXML
	private void initialize() throws IOException{
		Ba.setOnAction(event ->{
			try
			{
				model.swapScene('m');
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Add.setOnAction(event ->{
			Dialog<ArrayList<String>> adddiag = new Dialog<>();
			adddiag.setTitle("Add/Edit an Employee...");	
			adddiag.setHeaderText("Add or Edit a Customer's data");
			ButtonType savButtonType = new ButtonType("Save", ButtonData.OK_DONE);
			adddiag.getDialogPane().getButtonTypes().addAll(savButtonType, ButtonType.CANCEL);
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			TextField name = new TextField();
			name.setPromptText("name");
			TextField phone = new TextField();
			phone.setPromptText("phone");
			grid.add(new Label("Name:"), 0, 0);
			grid.add(name, 1, 0);
			grid.add(new Label("Phone:"), 0, 1);
			grid.add(phone, 1, 1);
			adddiag.getDialogPane().setContent(grid);
			Platform.runLater(() -> name.requestFocus());
			adddiag.setResultConverter(dialogButton -> {
				if (dialogButton == savButtonType){
					ArrayList<String> Result = new ArrayList<>();
					Result.add(name.getText());
					Result.add(phone.getText());
					return Result;
				}
				return null;
			});
			Optional<ArrayList<String>> newEntry = adddiag.showAndWait();
		});
	}
}

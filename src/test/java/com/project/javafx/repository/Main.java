package com.project.javafx.repository;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableRowExpanderColumn;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
	
		StackPane root = new StackPane();
		
		TableView<YouTubers> table = new TableView<>();
		table.setPrefWidth(300);
		
		TableRowExpanderColumn<YouTubers> expander = new TableRowExpanderColumn<>(this::createEditor);
		
		TableColumn<YouTubers, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		TableColumn<YouTubers, String> nameColumn = new TableColumn<>("NAME");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<YouTubers, String> emailColumn = new TableColumn<>("EMAIL");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		table.getColumns().addAll(expander,idColumn,nameColumn,emailColumn);
		table.setItems(getYouTubers());
		
		root.getChildren().add(table);
		
		Scene scene = new Scene(root,500,500);
		stage.setScene(scene);
		stage.show();
			
	}

	
    private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<YouTubers> param) {
		
    	GridPane editor = new GridPane();
    	editor.setPadding(new Insets(10));
    	editor.setHgap(10);
    	editor.setVgap(5);
    	
    	YouTubers youtube = param.getValue();
    	
    	TextField nameText = new TextField(youtube.getName());
    	TextField emailText = new TextField(youtube.getEmail());
    	
    	editor.addRow(0, new Label("Name"), nameText);
    	editor.addRow(1, new Label("Email"), emailText);
    	
    	Button save = new Button("Save");
    	save.setOnAction(e -> {
    		
    		youtube.setName(nameText.getText());
    		youtube.setEmail(emailText.getText());
    		param.toggleExpanded();
    	});
		
    	Button cancel = new Button("Cancel");
    	cancel.setOnAction(e1 -> param.toggleExpanded());
    	
    	editor.addRow(2, save,cancel);
    	
		return editor;
		
	 }
    
    
    public ObservableList<YouTubers> getYouTubers()
    {
		return FXCollections.observableArrayList(
                new YouTubers(1, "Genius Coders", "codersgenius@gmail.com"),
                new YouTubers(2, "Tom", "tom123@yahoo.com"),
                new YouTubers(3, "Subscribers", "sub03@gmail.com")
                
        );
    	
    }
	
	public static void main(String args[])
	{
		launch(args);
	}
	
	public static class YouTubers
	{
		public SimpleIntegerProperty idProperty = new SimpleIntegerProperty(this, "id");
        public SimpleStringProperty nameProperty = new SimpleStringProperty(this, "name");
        public SimpleStringProperty emailProperty = new SimpleStringProperty(this, "email");

        public YouTubers(Integer id, String name, String email) {
            setId(id);
            setName(name);
            setEmail(email);
        }

        public Integer getId() {
            return idProperty.get();
        }

        public SimpleIntegerProperty idProperty() {
            return idProperty;
        }

        public void setId(int id) {
            this.idProperty.set(id);
        }

        public String getName() {
            return nameProperty.get();
        }

        public SimpleStringProperty nameProperty() {
            return nameProperty;
        }

        public void setName(String name) {
            this.nameProperty.set(name);
        }

        public String getEmail() {
            return emailProperty.get();
        }

        public SimpleStringProperty emailProperty() {
            return emailProperty;
        }

        public void setEmail(String email) {
            this.emailProperty.set(email);
        }

			
	}
	
	
}
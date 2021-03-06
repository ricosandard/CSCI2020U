package lab08;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class lab08 extends Application {

	// The Data ----
	private TableView<StudentRecord> table = new TableView<StudentRecord>();
	private final ObservableList<StudentRecord> marks = FXCollections.observableArrayList(
			new StudentRecord("100100100", 75.0f, 68.0f, 54.25f), new StudentRecord("100100101", 70.0f, 69.25f, 51.5f),
			new StudentRecord("100100102", 100.0f, 97.0f, 92.5f), new StudentRecord("100100103", 90.0f, 88.5f, 68.75f),
			new StudentRecord("100100104", 72.25f, 74.75f, 58.25f), new StudentRecord("100100105", 85.0f, 56.0f, 62.5f),
			new StudentRecord("100100106", 70.0f, 66.5f, 61.75f), new StudentRecord("100100107", 55.0f, 47.0f, 50.5f),
			new StudentRecord("100100108", 40.0f, 32.5f, 27.75f), new StudentRecord("100100109", 82.5f, 77.0f, 74.25f));
	// --------------
	final HBox hb = new HBox();
	private String currentFilename = "lab08.csv";

	public static class StudentRecord {

		private final SimpleStringProperty sid;
		private final SimpleFloatProperty assignment;
		private final SimpleFloatProperty midterm;
		private final SimpleFloatProperty exam;
		private double mark = 0;
		private String grade = "Z";

		private StudentRecord(String SID, float Assignment, float Midterm, float Exam) {
			this.sid = new SimpleStringProperty(SID);
			this.assignment = new SimpleFloatProperty(Assignment);
			this.midterm = new SimpleFloatProperty(Midterm);
			this.exam = new SimpleFloatProperty(Exam);

			mark = assignment.get() * 0.2 + midterm.get() * 0.3 + exam.get() * 0.5;

			if (mark >= 80 && mark <= 100) {
				grade = "A";
			}
			if (mark >= 70 && mark < 80) {
				grade = "B";
			}
			if (mark >= 60 && mark < 70) {
				grade = "C";
			}
			if (mark >= 50 && mark < 60) {
				grade = "D";
			}
			if (mark >= 0 && mark < 50) {
				grade = "F";
			}
		}

		public String getSID() {
			return sid.get();
		}

		public void setSID(String SID) {
			sid.set(SID);
		}

		public float getAssignment() {
			return assignment.get();
		}

		public void setAssignment(float Assignment) {
			assignment.set(Assignment);
		}

		public float getMidterm() {
			return midterm.get();
		}

		public void setMidterm(float Midterm) {
			midterm.set(Midterm);
		}

		public float getExam() {
			return exam.get();
		}

		public void setExam(float Exam) {
			exam.set(Exam);
		}

		public double getMark() {
			return mark;
		}

		public String getGrade() {
			return grade;
		}
	}

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("lab08");
		stage.setWidth(650);
		stage.setHeight(550);

		// create a menu
		Menu m = new Menu("Menu");
		// create menuitems
		MenuItem newBt = new MenuItem("New");
		MenuItem openBt = new MenuItem("Open");
		MenuItem saveBt = new MenuItem("Save");
		MenuItem saveAsBt = new MenuItem("Save As");
		MenuItem exitBt = new MenuItem("Exit");
		// add menu items to menu
		m.getItems().addAll(newBt, openBt, saveBt, saveAsBt, exitBt);
		// create a menubar
		MenuBar mb = new MenuBar();
		// add menu to menubar
		mb.getMenus().add(m);

		// Table
		TableColumn sidCol = new TableColumn("SID");
		sidCol.setMinWidth(100);
		sidCol.setCellValueFactory(new PropertyValueFactory<StudentRecord, String>("SID"));

		TableColumn assignmentCol = new TableColumn("Assignment");
		assignmentCol.setMinWidth(100);
		assignmentCol.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("assignment"));

		TableColumn midtermCol = new TableColumn("Midterm");
		midtermCol.setMinWidth(100);
		midtermCol.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("midterm"));

		TableColumn examCol = new TableColumn("Final Exam");
		examCol.setMinWidth(100);
		examCol.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("exam"));

		TableColumn markCol = new TableColumn("Final Mark");
		markCol.setMinWidth(100);
		markCol.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("mark"));

		TableColumn gradeCol = new TableColumn("Letter Grade");
		gradeCol.setMinWidth(100);
		gradeCol.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("grade"));

		table.setItems(marks);
		table.getColumns().addAll(sidCol, assignmentCol, midtermCol, examCol, markCol, gradeCol);

		// Add
		final TextField addSID = new TextField();
		addSID.setPromptText("SID");
		addSID.setMaxWidth(sidCol.getPrefWidth());

		final TextField addAssignment = new TextField();
		addAssignment.setMaxWidth(assignmentCol.getPrefWidth());
		addAssignment.setPromptText("Assignmment");

		final TextField addMidterm = new TextField();
		addMidterm.setMaxWidth(midtermCol.getPrefWidth());
		addMidterm.setPromptText("Midterm");

		final TextField addExam = new TextField();
		addExam.setMaxWidth(examCol.getPrefWidth());
		addExam.setPromptText("Final Exam");

		final Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				marks.add(new StudentRecord(addSID.getText(), Float.parseFloat(addAssignment.getText()),
						Float.parseFloat(addMidterm.getText()), Float.parseFloat(addExam.getText())));
				addSID.clear();
				addAssignment.clear();
				addMidterm.clear();
				addExam.clear();
			}
		});
		
		newBt.setOnAction(e -> {
			table.getItems().clear();
		});

		saveBt.setOnAction(e -> {
			save();
		});

		openBt.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files", "*.csv"));
			File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				currentFilename = selectedFile.getAbsolutePath();
				load();
			}
		});

		saveAsBt.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files", "*.csv"));
			File selectedFile = fileChooser.showSaveDialog(stage);
			if (selectedFile != null) {
				currentFilename = selectedFile.getAbsolutePath();
				save();
			}
		});
		
		exitBt.setOnAction(e -> {
			System.exit(0);
		});
		
		hb.getChildren().addAll(addSID, addAssignment, addMidterm, addExam, addButton);
		hb.setSpacing(3);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(mb, table, hb);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();
	}

	public void save() {
		try (PrintWriter output = new PrintWriter(new File(currentFilename))) {
			output.write("SID,Assignments,Midterm,Final Exam\n");
			marks.forEach((student) -> {
				output.write(student.getSID() + "," + student.getAssignment() + "," + student.getMidterm() + ","
						+ student.getExam() + "\n");
			});

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public void load() {
		table.getItems().clear();
		try (Scanner input = new Scanner(new File(currentFilename))) {
			input.nextLine();
			while (input.hasNext()) {
				String[] line = input.next().split(",");
				
				marks.add(new StudentRecord(line[0], Float.parseFloat(line[1]),
						Float.parseFloat(line[2]), Float.parseFloat(line[3])));
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
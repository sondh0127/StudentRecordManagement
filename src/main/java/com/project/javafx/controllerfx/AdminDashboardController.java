package com.project.javafx.controllerfx;


import com.jfoenix.controls.JFXButton;
import com.project.javafx.MainApp;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.repository.CreditMajorRepository;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.ViewConstants;
import com.project.javafx.ulti.AlertMaker;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private JFXButton btnCourse;

    @FXML
    private JFXButton btnAnnual;

    @FXML
    private JFXButton btnCredit;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXButton btnSetting;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnExit;

    @FXML
    private JFXButton btn_minimize;

    @FXML
    private JFXButton btn_maximize;

    @FXML
    private JFXButton btn_close;

    @FXML
    private JFXButton btn_option;

    @FXML
    private StackPane holderPane;
    private Parent homePane, studentPane, coursePane, registerPane, settingPane, majorPane, classPane;

    private static MainApp mainApp;

    public static void setMainApp(MainApp mainApp) {
        AdminDashboardController.mainApp = mainApp;
    }

    private static final String registrationDataFile = "src/registration.xml";
    private static final String studentDataFile = "src/students.xml";
    private static final String majorDataFile = "src/majors.xml";
    private static final String classDataFile = "src/classes.xml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataOnStart();
//        openMenus();
        try {
            homePane = FXMLLoader.load(getClass().getResource(ViewConstants.HOME_VIEW));
            studentPane = FXMLLoader.load(getClass().getResource(ViewConstants.STUDENTS_VIEW));
            coursePane = FXMLLoader.load(getClass().getResource(ViewConstants.COURSES_VIEW));
            registerPane = FXMLLoader.load(getClass().getResource(ViewConstants.REGISTER_VIEW));
            settingPane = FXMLLoader.load(getClass().getResource(ViewConstants.SETTINGS_VIEW));
            majorPane = FXMLLoader.load(getClass().getResource(ViewConstants.MAJORS_VIEW));
        } catch (IOException e) {
//            AlertMaker.showErrorMessage(e);
            e.printStackTrace();
        }
        //set up default node on page load
        setNode(homePane);
        // loading data from file

    }
//    private void openMenus() {
//        JFXPopup popup = new JFXPopup(overflowContainer);
//        lblMenu.setOnMouseClicked(event -> {popup.show(lblMenu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, -10, 50);});
//    }
    /**
     * Set selected node to a content holder
     */
    private void setNode(Node node) {
        holderPane.getChildren().clear();
        if (node != null) {
            holderPane.getChildren().add(node);
        }

        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    void openCourse(ActionEvent event) {
        setNode(coursePane);
    }

    @FXML
    void openAnnual(ActionEvent event) {

    }

    @FXML
    void openCredit(ActionEvent event) {
        setNode(majorPane);
    }


    @FXML
    void openHome(ActionEvent event) {
        setNode(homePane);
    }

    @FXML
    void openRegister(ActionEvent event) {
        setNode(registerPane);
    }

    @FXML
    void openSetting(ActionEvent event) {
        setNode(settingPane);
    }

    @FXML
    void openStudent(ActionEvent event) {
        setNode(studentPane);
    }

    public void logOff(ActionEvent event) throws IOException {
        boolean logoffCf = AlertMaker.getConfirmation("Logout Confirmation", "Are you want to logout?");
        if (logoffCf) {
            Stage currentStage = (Stage) btnLogOut.getScene().getWindow();
            currentStage.close();
            saveDataOnClose();
            mainApp.showLoginStage();
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        boolean exitCf = AlertMaker.getConfirmation("Exit Confirmation", "Are you sure want to exit?");
        if (exitCf) {
            saveDataOnClose();
            Platform.exit();
        }
    }

    private void loadDataOnStart() {
        // init
        CreditMajorRepository.getInstance().initSomeMajor();
        AnnualClassRepository.getInstance().initSomeClass();
//        StudentRepository.getInstance().initSomeStudent();
        CourseRepository.getInstance().initCourses();

        //load from file
//        CreditMajorRepository.getInstance().gSonLoad();
//        AnnualClassRepository.getInstance().gSonLoad();
        StudentRepository.getInstance().gSonLoad();
//        CourseRepository.getInstance().gSonLoad();
    }

    private void saveDataOnClose() {
        StudentRepository.getInstance().gSonSave();
        CourseRepository.getInstance().gSonSave();
        CreditMajorRepository.getInstance().gSonSave();
        AnnualClassRepository.getInstance().gSonSave();
    }

    @FXML
    public void minimize(MouseEvent event) {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);
    }

    private double lastX = 0.0d;
    private double lastY = 0.0d;
    private double lastWidth = 0.0d;
    private double lastHeight = 0.0d;

    @FXML
    public void maximize(MouseEvent evt) {

        Node n = (Node)evt.getSource();

        Window w = n.getScene().getWindow();

        double currentX = w.getX();
        double currentY = w.getY();
        double currentWidth = w.getWidth();
        double currentHeight = w.getHeight();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        if( currentX != bounds.getMinX() &&
                currentY != bounds.getMinY() &&
                currentWidth != bounds.getWidth() &&
                currentHeight != bounds.getHeight() ) {

            w.setX(bounds.getMinX());
            w.setY(bounds.getMinY());
            w.setWidth(bounds.getWidth());
            w.setHeight(bounds.getHeight());

            lastX = currentX; // save old dimensions
            lastY = currentY;
            lastWidth = currentWidth;
            lastHeight = currentHeight;

        } else {

            // de-maximize the window (not same as minimize)

            w.setX(lastX);
            w.setY(lastY);
            w.setWidth(lastWidth);
            w.setHeight(lastHeight);
        }

        evt.consume(); // don't bubble up to title bar
    }

}

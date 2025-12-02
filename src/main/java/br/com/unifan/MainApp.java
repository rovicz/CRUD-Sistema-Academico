package br.com.unifan;

import br.com.unifan.view.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema Acadêmico - UNIFAN");
        rootLayout = new BorderPane();

        MenuBar menuBar = new MenuBar();
        Menu menuCadastros = new Menu("Cadastros");

        MenuItem itemCursos = new MenuItem("Cursos");
        MenuItem itemDisciplinas = new MenuItem("Disciplinas");
        MenuItem itemProfessores = new MenuItem("Professores");
        MenuItem itemTurmas = new MenuItem("Turmas");

        itemCursos.setOnAction(e -> rootLayout.setCenter(new CursoView()));
        itemDisciplinas.setOnAction(e -> rootLayout.setCenter(new DisciplinaView()));
        itemProfessores.setOnAction(e -> rootLayout.setCenter(new ProfessorView()));
        itemTurmas.setOnAction(e -> rootLayout.setCenter(new TurmaView()));

        menuCadastros.getItems().addAll(itemCursos, itemProfessores, itemDisciplinas, itemTurmas);
        menuBar.getMenus().add(menuCadastros);

        rootLayout.setTop(menuBar);

        Label welcome = new Label("Bem-vindo ao Sistema Acadêmico");
        welcome.setStyle("-fx-font-size: 24px; -fx-text-fill: #94a3b8;");
        welcome.setPadding(new Insets(20));
        rootLayout.setCenter(welcome);

        Scene scene = new Scene(rootLayout, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
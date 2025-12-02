package br.com.unifan.view;

import br.com.unifan.controller.BaseController;
import br.com.unifan.dao.CursoDAO;
import br.com.unifan.model.Curso;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class CursoView extends VBox {
    private TextField txtNome = new TextField();
    private TextField txtCarga = new TextField();
    private TableView<Curso> tabela = new TableView<>();
    private BaseController<Curso> controller;
    private Curso cursoSelecionado;

    public CursoView() {
        this.controller = new BaseController<>(new CursoDAO()) {};
        configurarUI();
        controller.carregarDados();
        tabela.setItems(controller.getLista());
    }

    private void configurarUI() {
        setSpacing(20);
        getStyleClass().add("content-pane");
        setStyle("-fx-padding: 30;");

        Label titulo = new Label("Gerenciamento de Cursos");
        titulo.getStyleClass().add("title-label");

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);

        txtNome.setPromptText("Nome do Curso");
        txtCarga.setPromptText("Carga em Horas");

        form.add(new Label("Nome:"), 0, 0);
        form.add(txtNome, 1, 0);
        form.add(new Label("Carga em Horas:"), 0, 1);
        form.add(txtCarga, 1, 1);

        HBox botoes = new HBox(10);
        Button btnSalvar = new Button("Salvar");
        btnSalvar.getStyleClass().add("button-primary");
        Button btnLimpar = new Button("Limpar");
        Button btnExcluir = new Button("Excluir");
        btnExcluir.getStyleClass().add("button-danger");

        botoes.getChildren().addAll(btnSalvar, btnLimpar, btnExcluir);

        TableColumn<Curso, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Curso, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(300);

        TableColumn<Curso, Integer> colCarga = new TableColumn<>("Carga em Horas");
        colCarga.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));

        tabela.getColumns().addAll(colId, colNome, colCarga);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
            if (novo != null) {
                cursoSelecionado = novo;
                txtNome.setText(novo.getNome());
                txtCarga.setText(String.valueOf(novo.getCargaHoraria()));
            }
        });

        btnSalvar.setOnAction(e -> {
            if (cursoSelecionado == null) cursoSelecionado = new Curso();
            cursoSelecionado.setNome(txtNome.getText());
            cursoSelecionado.setCargaHoraria(Integer.parseInt(txtCarga.getText()));

            if (cursoSelecionado.getId() == null) controller.salvar(cursoSelecionado);
            else controller.atualizar(cursoSelecionado);
            limpar();
        });

        btnExcluir.setOnAction(e -> {
            if (cursoSelecionado != null) {
                controller.excluir(cursoSelecionado, cursoSelecionado.getId());
                limpar();
            }
        });

        btnLimpar.setOnAction(e -> limpar());

        getChildren().addAll(titulo, form, botoes, tabela);
    }

    private void limpar() {
        txtNome.clear();
        txtCarga.clear();
        cursoSelecionado = null;
        tabela.getSelectionModel().clearSelection();
    }
}
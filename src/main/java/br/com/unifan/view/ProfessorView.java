package br.com.unifan.view;

import br.com.unifan.controller.BaseController;
import br.com.unifan.dao.ProfessorDAO;
import br.com.unifan.model.Professor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class ProfessorView extends VBox {
    private TextField txtNome = new TextField();
    private TextField txtEmail = new TextField();
    private TextField txtFormacao = new TextField();
    private TableView<Professor> tabela = new TableView<>();
    private BaseController<Professor> controller;
    private Professor selecionado;

    public ProfessorView() {
        this.controller = new BaseController<>(new ProfessorDAO()) {};
        configurarUI();
        controller.carregarDados();
        tabela.setItems(controller.getLista());
    }

    private void configurarUI() {
        setSpacing(20);
        setStyle("-fx-padding: 30;");

        Label titulo = new Label("Gerenciamento de Professores");
        titulo.getStyleClass().add("title-label");

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);

        form.add(new Label("Nome:"), 0, 0);
        form.add(txtNome, 1, 0);
        form.add(new Label("Email:"), 0, 1);
        form.add(txtEmail, 1, 1);
        form.add(new Label("Formação:"), 0, 2);
        form.add(txtFormacao, 1, 2);

        HBox botoes = new HBox(10);
        Button btnSalvar = new Button("Salvar");
        btnSalvar.getStyleClass().add("button-primary");
        Button btnLimpar = new Button("Limpar");
        Button btnExcluir = new Button("Excluir");
        btnExcluir.getStyleClass().add("button-danger");
        botoes.getChildren().addAll(btnSalvar, btnLimpar, btnExcluir);

        TableColumn<Professor, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Professor, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Professor, String> colFormacao = new TableColumn<>("Formação");
        colFormacao.setCellValueFactory(new PropertyValueFactory<>("formacao"));

        tabela.getColumns().addAll(colNome, colEmail, colFormacao);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
            if (novo != null) {
                selecionado = novo;
                txtNome.setText(novo.getNome());
                txtEmail.setText(novo.getEmail());
                txtFormacao.setText(novo.getFormacao());
            }
        });

        btnSalvar.setOnAction(e -> {
            if (selecionado == null) selecionado = new Professor();
            selecionado.setNome(txtNome.getText());
            selecionado.setEmail(txtEmail.getText());
            selecionado.setFormacao(txtFormacao.getText());
            if (selecionado.getId() == null) controller.salvar(selecionado);
            else controller.atualizar(selecionado);
            limpar();
        });

        btnExcluir.setOnAction(e -> {
            if (selecionado != null) {
                controller.excluir(selecionado, selecionado.getId());
                limpar();
            }
        });

        btnLimpar.setOnAction(e -> limpar());

        getChildren().addAll(titulo, form, botoes, tabela);
    }

    private void limpar() {
        txtNome.clear();
        txtEmail.clear();
        txtFormacao.clear();
        selecionado = null;
        tabela.getSelectionModel().clearSelection();
    }
}
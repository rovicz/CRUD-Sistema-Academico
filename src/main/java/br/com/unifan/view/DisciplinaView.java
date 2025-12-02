package br.com.unifan.view;

import br.com.unifan.controller.BaseController;
import br.com.unifan.dao.CursoDAO;
import br.com.unifan.dao.DisciplinaDAO;
import br.com.unifan.model.Curso;
import br.com.unifan.model.Disciplina;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class DisciplinaView extends VBox {
    private TextField txtNome = new TextField();
    private TextField txtDesc = new TextField();
    private ComboBox<Curso> cbCurso = new ComboBox<>();
    private TableView<Disciplina> tabela = new TableView<>();
    private BaseController<Disciplina> controller;
    private Disciplina selecionado;

    public DisciplinaView() {
        this.controller = new BaseController<>(new DisciplinaDAO()) {};
        configurarUI();
        carregarDados();
    }

    private void carregarDados() {
        controller.carregarDados();
        tabela.setItems(controller.getLista());
        cbCurso.getItems().setAll(new CursoDAO().findAll());
    }

    private void configurarUI() {
        setSpacing(20);
        setStyle("-fx-padding: 30;");

        Label titulo = new Label("Gerenciamento de Disciplinas");
        titulo.getStyleClass().add("title-label");

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);

        form.add(new Label("Nome:"), 0, 0);
        form.add(txtNome, 1, 0);
        form.add(new Label("Descrição:"), 0, 1);
        form.add(txtDesc, 1, 1);
        form.add(new Label("Curso:"), 0, 2);
        form.add(cbCurso, 1, 2);

        HBox botoes = new HBox(10);
        Button btnSalvar = new Button("Salvar");
        btnSalvar.getStyleClass().add("button-primary");
        Button btnLimpar = new Button("Limpar");
        Button btnExcluir = new Button("Excluir");
        btnExcluir.getStyleClass().add("button-danger");

        botoes.getChildren().addAll(btnSalvar, btnLimpar, btnExcluir);

        TableColumn<Disciplina, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Disciplina, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Disciplina, String> colCurso = new TableColumn<>("Curso");
        colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));

        tabela.getColumns().addAll(colNome, colDesc, colCurso);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
            if (novo != null) {
                selecionado = novo;
                txtNome.setText(novo.getNome());
                txtDesc.setText(novo.getDescricao());
                cbCurso.setValue(novo.getCurso());
            }
        });

        btnSalvar.setOnAction(e -> {
            if (selecionado == null) selecionado = new Disciplina();
            selecionado.setNome(txtNome.getText());
            selecionado.setDescricao(txtDesc.getText());
            selecionado.setCurso(cbCurso.getValue());

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
        txtDesc.clear();
        cbCurso.getSelectionModel().clearSelection();
        selecionado = null;
        tabela.getSelectionModel().clearSelection();
    }
}
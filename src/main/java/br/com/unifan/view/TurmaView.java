package br.com.unifan.view;

import br.com.unifan.controller.BaseController;
import br.com.unifan.dao.DisciplinaDAO;
import br.com.unifan.dao.ProfessorDAO;
import br.com.unifan.dao.TurmaDAO;
import br.com.unifan.model.Disciplina;
import br.com.unifan.model.Professor;
import br.com.unifan.model.Turma;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class TurmaView extends VBox {
    private TextField txtSemestre = new TextField();
    private TextField txtHorario = new TextField();
    private ComboBox<Disciplina> cbDisciplina = new ComboBox<>();
    private ComboBox<Professor> cbProfessor = new ComboBox<>();
    private TableView<Turma> tabela = new TableView<>();
    private BaseController<Turma> controller;
    private Turma selecionado;

    public TurmaView() {
        this.controller = new BaseController<>(new TurmaDAO()) {};
        configurarUI();
        carregarDados();
    }

    private void carregarDados() {
        controller.carregarDados();
        tabela.setItems(controller.getLista());
        cbDisciplina.getItems().setAll(new DisciplinaDAO().findAll());
        cbProfessor.getItems().setAll(new ProfessorDAO().findAll());
    }

    private void configurarUI() {
        setSpacing(20);
        setStyle("-fx-padding: 30;");
        Label titulo = new Label("Gerenciamento de Turmas");
        titulo.getStyleClass().add("title-label");

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.add(new Label("Semestre:"), 0, 0);
        form.add(txtSemestre, 1, 0);
        form.add(new Label("Horário:"), 0, 1);
        form.add(txtHorario, 1, 1);
        form.add(new Label("Disciplina:"), 0, 2);
        form.add(cbDisciplina, 1, 2);
        form.add(new Label("Professor:"), 0, 3);
        form.add(cbProfessor, 1, 3);

        HBox botoes = new HBox(10);
        Button btnSalvar = new Button("Salvar");
        btnSalvar.getStyleClass().add("button-primary");
        Button btnLimpar = new Button("Limpar");
        Button btnExcluir = new Button("Excluir");
        btnExcluir.getStyleClass().add("button-danger");
        botoes.getChildren().addAll(btnSalvar, btnLimpar, btnExcluir);

        TableColumn<Turma, String> colSem = new TableColumn<>("Semestre");
        colSem.setCellValueFactory(new PropertyValueFactory<>("semestre"));
        TableColumn<Turma, String> colDisc = new TableColumn<>("Disciplina");
        colDisc.setCellValueFactory(new PropertyValueFactory<>("disciplina"));
        TableColumn<Turma, String> colProf = new TableColumn<>("Professor");
        colProf.setCellValueFactory(new PropertyValueFactory<>("professor"));

        tabela.getColumns().addAll(colSem, colDisc, colProf);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
            if (novo != null) {
                selecionado = novo;
                txtSemestre.setText(novo.getSemestre());
                txtHorario.setText(novo.getHorario());
                cbDisciplina.setValue(novo.getDisciplina());
                cbProfessor.setValue(novo.getProfessor());
            }
        });

        btnSalvar.setOnAction(e -> {
            if (selecionado == null) selecionado = new Turma();
            selecionado.setSemestre(txtSemestre.getText());
            selecionado.setHorario(txtHorario.getText());
            selecionado.setDisciplina(cbDisciplina.getValue());
            selecionado.setProfessor(cbProfessor.getValue());
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
        txtSemestre.clear();
        txtHorario.clear();
        cbDisciplina.getSelectionModel().clearSelection();
        cbProfessor.getSelectionModel().clearSelection();
        selecionado = null;
        tabela.getSelectionModel().clearSelection();
    }
}
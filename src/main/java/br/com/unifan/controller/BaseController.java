package br.com.unifan.controller;

import br.com.unifan.dao.GenericDAO;
import br.com.unifan.utils.AlertaUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public abstract class BaseController<T> {
    protected GenericDAO<T> dao;
    protected ObservableList<T> listaDados;
    protected TableView<T> tabela;

    public BaseController(GenericDAO<T> dao) {
        this.dao = dao;
        this.listaDados = FXCollections.observableArrayList();
    }

    public void carregarDados() {
        listaDados.clear();
        listaDados.addAll(dao.findAll());
    }

    public void salvar(T entidade) {
        try {
            dao.create(entidade);
            carregarDados();
            AlertaUtil.exibirInfo("Sucesso", "Registro salvo com sucesso!");
        } catch (Exception e) {
            AlertaUtil.exibirErro("Erro", "Erro ao salvar: " + e.getMessage());
        }
    }

    public void atualizar(T entidade) {
        try {
            dao.update(entidade);
            carregarDados();
            AlertaUtil.exibirInfo("Sucesso", "Registro atualizado com sucesso!");
        } catch (Exception e) {
            AlertaUtil.exibirErro("Erro", "Erro ao atualizar: " + e.getMessage());
        }
    }

    public void excluir(T entidade, Long id) {
        if (AlertaUtil.exibirConfirmacao("Excluir", "Deseja realmente excluir este item?")) {
            try {
                dao.delete(id);
                carregarDados();
                AlertaUtil.exibirInfo("Sucesso", "Removido com sucesso!");
            } catch (Exception e) {
                AlertaUtil.exibirErro("Erro", "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    public ObservableList<T> getLista() { return listaDados; }
}
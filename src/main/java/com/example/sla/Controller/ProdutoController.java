package com.example.sla.Controller;

import com.example.sla.Entity.Produto;
import com.example.sla.util.CSVUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;

public class ProdutoController {
    @FXML private TextField txtCodigo, txtNome, txtPreco, txtQuantidade;
    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, String> colCodigo, colNome;
    @FXML private TableColumn<Produto, BigDecimal> colPreco;
    @FXML private TableColumn<Produto, Integer> colQuantidade;

    private ObservableList<Produto> produtos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarProdutos();
    }

    private void configurarTabela() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tabelaProdutos.setItems(produtos);
    }

    private void carregarProdutos() {
        produtos.clear();
        produtos.addAll(CSVUtil.carregarProdutos());
    }

    @FXML
    private void adicionarProduto() {
        try {
            if (txtCodigo.getText().isEmpty() || txtNome.getText().isEmpty() ||
                    txtPreco.getText().isEmpty() || txtQuantidade.getText().isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos!", Alert.AlertType.ERROR);
                return;
            }

            Produto produto = new Produto(
                    txtCodigo.getText(),
                    txtNome.getText(),
                    new BigDecimal(txtPreco.getText()),
                    Integer.parseInt(txtQuantidade.getText())
            );

            CSVUtil.salvarProduto(produto);
            carregarProdutos();
            limparCampos();

            mostrarAlerta("Sucesso", "Produto adicionado com sucesso!", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Valores numéricos inválidos!", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao adicionar produto: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void removerProduto() {
        Produto selecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            CSVUtil.removerProduto(selecionado);
            carregarProdutos();
            mostrarAlerta("Sucesso", "Produto removido com sucesso!", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Aviso", "Nenhum produto selecionado", Alert.AlertType.WARNING);
        }
    }

    private void limparCampos() {
        txtCodigo.clear();
        txtNome.clear();
        txtPreco.clear();
        txtQuantidade.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
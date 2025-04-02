package com.example.sla.Controller;

import com.example.sla.Entity.Produto;
import com.example.sla.util.CSVUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class MainController {
    @FXML private TextField txtCodigo, txtNome, txtPreco, txtQuantidade, txtBuscaCodigo;
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
        // Remove qualquer destaque ao recarregar
        tabelaProdutos.setRowFactory(tv -> new TableRow<Produto>() {
            @Override
            protected void updateItem(Produto item, boolean empty) {
                super.updateItem(item, empty);
                setStyle("");
            }
        });
    }

    @FXML
    private void adicionarProduto() {
        try {
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
        } else {
            mostrarAlerta("Aviso", "Nenhum produto selecionado", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void buscarProdutoPorCodigo() {
        String codigoBusca = txtBuscaCodigo.getText().trim();
        if (codigoBusca.isEmpty()) {
            mostrarAlerta("Aviso", "Digite um código para buscar", Alert.AlertType.WARNING);
            return;
        }

        List<Produto> todosProdutos = CSVUtil.carregarProdutos();
        Optional<Produto> produtoEncontrado = todosProdutos.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigoBusca))
                .findFirst();

        produtos.clear();

        if (produtoEncontrado.isPresent()) {
            produtos.add(produtoEncontrado.get());
            // Destaca a linha do produto encontrado
            tabelaProdutos.setRowFactory(tv -> new TableRow<Produto>() {
                @Override
                protected void updateItem(Produto item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty && item.getCodigo().equalsIgnoreCase(codigoBusca)) {
                        setStyle("-fx-background-color: #e3f2fd; -fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                }
            });
        } else {
            mostrarAlerta("Informação", "Nenhum produto encontrado com o código: " + codigoBusca, Alert.AlertType.INFORMATION);
            carregarProdutos();
        }
    }

    @FXML
    private void limparBusca() {
        txtBuscaCodigo.clear();
        carregarProdutos();
    }

    @FXML
    private void irParaVendas() {
        try {
            Stage stage = (Stage) tabelaProdutos.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/sla/venda_view.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("TELA DE VENDAS");
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao abrir tela de vendas: " + e.getMessage(), Alert.AlertType.ERROR);
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
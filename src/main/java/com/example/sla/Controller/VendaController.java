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
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.List;

public class VendaController {
    @FXML private ComboBox<Produto> cbProdutos;
    @FXML private TextField txtQuantidade, txtTotal, txtValorRecebido, txtTroco;
    @FXML private TableView<Produto> tabelaItens;
    @FXML private TableColumn<Produto, String> colCodigo, colNome;
    @FXML private TableColumn<Produto, Integer> colQuantidade;
    @FXML private TableColumn<Produto, BigDecimal> colPreco;

    private ObservableList<Produto> itensVenda = FXCollections.observableArrayList();
    private BigDecimal totalVenda = BigDecimal.ZERO;

    @FXML
    public void initialize() {
        configurarTabela();
        carregarProdutos();
        configurarComboBox();
    }

    private void configurarTabela() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tabelaItens.setItems(itensVenda);
        tabelaItens.setStyle("-fx-background-color: white; -fx-border-color: #d1d5db; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    private void carregarProdutos() {
        cbProdutos.getItems().setAll(CSVUtil.carregarProdutos());
    }

    private void configurarComboBox() {
        cbProdutos.setConverter(new StringConverter<Produto>() {
            @Override
            public String toString(Produto produto) {
                return produto != null ? produto.getCodigo() + " - " + produto.getNome() : "";
            }

            @Override
            public Produto fromString(String string) {
                return null;
            }
        });

        cbProdutos.setCellFactory(param -> new ListCell<Produto>() {
            @Override
            protected void updateItem(Produto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getCodigo() + " - " + item.getNome());
            }
        });
    }

    @FXML
    private void adicionarItem() {
        Produto produto = cbProdutos.getValue();
        if (produto == null) {
            mostrarAlerta("Erro", "Selecione um produto", Alert.AlertType.ERROR);
            return;
        }

        try {
            int quantidade = Integer.parseInt(txtQuantidade.getText());
            if (quantidade <= 0) {
                mostrarAlerta("Erro", "Quantidade deve ser maior que zero", Alert.AlertType.ERROR);
                return;
            }

            if (quantidade > produto.getQuantidade()) {
                mostrarAlerta("Erro", "Quantidade indisponível em estoque", Alert.AlertType.ERROR);
                return;
            }

            Produto itemVenda = new Produto(
                    produto.getCodigo(),
                    produto.getNome(),
                    produto.getPreco(),
                    quantidade
            );

            itensVenda.add(itemVenda);
            atualizarTotal();
            txtQuantidade.clear();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Quantidade inválida", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void calcularTroco() {
        try {
            BigDecimal valorRecebido = new BigDecimal(txtValorRecebido.getText());
            BigDecimal troco = valorRecebido.subtract(totalVenda);

            if (troco.compareTo(BigDecimal.ZERO) < 0) {
                mostrarAlerta("Aviso", "Valor insuficiente", Alert.AlertType.WARNING);
                return;
            }

            txtTroco.setText(String.format("R$ %.2f", troco));
        } catch (Exception e) {
            mostrarAlerta("Erro", "Valor recebido inválido", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void finalizarVenda() {
        if (itensVenda.isEmpty()) {
            mostrarAlerta("Erro", "Adicione itens à venda", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Atualiza estoque
            List<Produto> produtos = CSVUtil.carregarProdutos();
            for (Produto item : itensVenda) {
                for (Produto p : produtos) {
                    if (p.getCodigo().equals(item.getCodigo())) {
                        p.setQuantidade(p.getQuantidade() - item.getQuantidade());
                        break;
                    }
                }
            }
            CSVUtil.salvarTodosProdutos(produtos);

            mostrarAlerta("Sucesso", "Venda realizada com sucesso!\nTotal: R$ " + String.format("%.2f", totalVenda), Alert.AlertType.INFORMATION);
            limparVenda();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao finalizar venda: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void voltarParaProdutos() {
        try {
            Stage stage = (Stage) tabelaItens.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/sla/main_view.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("GERENCIAMENTO DE PRODUTOS");
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao voltar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void atualizarTotal() {
        totalVenda = itensVenda.stream()
                .map(p -> p.getPreco().multiply(BigDecimal.valueOf(p.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        txtTotal.setText(String.format("R$ %.2f", totalVenda));
    }

    private void limparVenda() {
        itensVenda.clear();
        totalVenda = BigDecimal.ZERO;
        txtTotal.clear();
        txtValorRecebido.clear();
        txtTroco.clear();
        carregarProdutos();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
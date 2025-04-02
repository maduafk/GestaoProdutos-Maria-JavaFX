package com.example.sla.Controller;

import com.example.sla.Entity.Produto;
import com.example.sla.util.RelatorioUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PainelHistoricoController implements Initializable {

    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, String> colunaCodigo;
    @FXML private TableColumn<Produto, String> colunaNome;
    @FXML private TableColumn<Produto, BigDecimal> colunaPreco;
    @FXML private TableColumn<Produto, Integer> colunaQuantidade;

    private ObservableList<Produto> produtos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabela();
        carregarDados();
        gerarRelatorios();
    }

    private void configurarTabela() {
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tabelaProdutos.setItems(produtos);
    }

    private void carregarDados() {
        // Substitua isso pela sua forma de carregar os produtos (JPA ou DAO)
        // List<Produto> listaProdutos = produtoDAO.listarTodos();
        // produtos.setAll(listaProdutos);
    }

    private void gerarRelatorios() {
        List<Produto> listaProdutos = produtos; // Ou carregue do seu DAO

        // Gerar relatório de estoque baixo
        List<Produto> estoqueBaixo = RelatorioUtil.getEstoqueBaixo(listaProdutos);
        RelatorioUtil.salvarRelatorioEstoqueBaixo(estoqueBaixo, "estoque_baixo.txt");

        // Gerar relatório de valor total
        BigDecimal totalEstoque = RelatorioUtil.getValorTotalEstoque(listaProdutos);
        try (PrintWriter writer = new PrintWriter(new FileWriter("valor_total_estoque.txt"))) {
            writer.println("VALOR TOTAL EM ESTOQUE: R$ " + totalEstoque);
        } catch (IOException e) {
            System.err.println("Erro ao salvar relatório: " + e.getMessage());
        }
    }

    @FXML
    private void atualizarTabela() {
        carregarDados();
        gerarRelatorios();
    }
}
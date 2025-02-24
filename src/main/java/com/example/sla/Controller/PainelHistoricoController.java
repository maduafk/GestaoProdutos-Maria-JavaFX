package com.example.sla.Controller;

import com.example.sla.Entity.IMCRegistro;
import com.example.sla.DAO.IMCRegistroDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javaio.ArquivoUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PainelHistoricoController implements Initializable {
    @FXML private TableView<IMCRegistro> tabelaHistorico;
    @FXML private TableColumn<IMCRegistro, String> colunaNome;
    @FXML private TableColumn<IMCRegistro, Integer> colunaPeso;
    @FXML private TableColumn<IMCRegistro, Double> colunaAltura;
    @FXML private TableColumn<IMCRegistro, Double> colunaIMC;
    @FXML private TableColumn<IMCRegistro, String> colunaClassificacao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configura as colunas da tabela
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colunaAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));
        colunaIMC.setCellValueFactory(new PropertyValueFactory<>("imc"));
        colunaClassificacao.setCellValueFactory(new PropertyValueFactory<>("classificacao"));

        // Carrega os dados do banco de dados
        carregarHistorico();
    }

    private void carregarHistorico() {
        List<IMCRegistro> registros = IMCRegistroDAO.listarTodos();
        tabelaHistorico.getItems().setAll(registros);
    }

    @FXML
    private void salvarDados() {
        List<IMCRegistro> registros = tabelaHistorico.getItems(); // Obtém os dados da tabela
        ArquivoUtil.salvarDadosCSV(registros, "dados_pessoas.txt"); // Salva os dados no arquivo
    }

    @FXML
    private void carregarDados() {
        List<IMCRegistro> registros = ArquivoUtil.carregarDadosCSV("dados_pessoas.txt"); // Carrega os dados do arquivo
        tabelaHistorico.getItems().setAll(registros); // Atualiza a tabela com os dados carregados
    }
}
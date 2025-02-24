package com.example.sla;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PainelHistoricoController implements Initializable {
    @FXML
    private TableView<IMCRegistro> tabelaHistorico;

    @FXML
    private TableColumn<IMCRegistro, String> colunaNome;

    @FXML
    private TableColumn<IMCRegistro, Integer> colunaPeso;

    @FXML
    private TableColumn<IMCRegistro, Double> colunaAltura;

    @FXML
    private TableColumn<IMCRegistro, Double> colunaIMC;

    @FXML
    private TableColumn<IMCRegistro, String> colunaClassificacao;

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
        // Obtém os registros do banco de dados
        List<IMCRegistro> registros = IMCRegistroDAO.listarTodos();

        // Adiciona os registros na tabela
        tabelaHistorico.getItems().setAll(registros);
    }
}
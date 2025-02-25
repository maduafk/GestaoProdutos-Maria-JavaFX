package com.example.sla.Controller;

import com.example.sla.Entity.IMCRegistro;
import com.example.sla.DAO.IMCRegistroDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class CalculoIMCController implements Initializable {
    @FXML private Slider slPeso;
    @FXML private Slider slAlt;
    @FXML private Label altL;
    @FXML private Label kiloL;
    @FXML private Text resultadoIMCText;
    @FXML private Label classificacaoIMC;
    @FXML private TextField nomeField;

    private int peso;
    private int altura;
    private double imc;
    private String classificacao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slPeso.valueProperty().addListener((observable, oldValue, newValue) -> {
            peso = newValue.intValue();
            kiloL.setText(peso + " KG");
        });

        slAlt.valueProperty().addListener((observable, oldValue, newValue) -> {
            altura = newValue.intValue();
            altL.setText(altura + " CM");
        });

        kiloL.setText("0 KG");
        altL.setText("75 CM");
        resultadoIMCText.setText("0.00");
        classificacaoIMC.setText("");
    }

    // Método para calcular o IMC
    @FXML
    public void btCalcular(ActionEvent actionEvent) {
        peso = (int) slPeso.getValue();
        altura = (int) slAlt.getValue();

        if (peso > 0 && altura > 0) {
            double alturaEmMetros = altura / 100.0;
            imc = peso / (alturaEmMetros * alturaEmMetros);
            resultadoIMCText.setText(String.format("%.2f", imc));

            classificacao = getClassificacao(imc);
            classificacaoIMC.setText(classificacao);
        } else {
            System.out.println("Peso e altura devem ser maiores que zero.");
        }
    }

    // Método para salvar no histórico
    @FXML
    public void btSalvar(ActionEvent actionEvent) {
        String nome = nomeField.getText().trim();
        if (!nome.isEmpty() && imc > 0) {
            // Cria um novo registro e salva no banco de dados
            IMCRegistro registro = new IMCRegistro(nome, peso, altura / 100.0, imc, classificacao);
            IMCRegistroDAO.salvar(registro); // Chama o método salvar
            System.out.println("Registro salvo com sucesso!");
        } else {
            System.out.println("Nome não pode estar vazio e o IMC deve ser calculado primeiro.");
        }
    }

    // Método para classificar o IMC
    private String getClassificacao(double imc) {
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 24.9) return "Peso Normal";
        if (imc < 29.9) return "Sobrepeso";
        if (imc < 34.9) return "Obesidade I";
        if (imc < 39.9) return "Obesidade II";
        return "Obesidade III";
    }
}
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

    private void calcularIMC() {
        peso = (int) slPeso.getValue();
        altura = (int) slAlt.getValue();

        if (peso > 0 && altura > 0) {
            double alturaEmMetros = altura / 100.0;
            double imc = peso / (alturaEmMetros * alturaEmMetros);
            resultadoIMCText.setText(String.format("%.2f", imc));

            String classificacao = getClassificacao(imc);
            classificacaoIMC.setText(classificacao);

            String nome = nomeField.getText().trim();
            if (!nome.isEmpty()) {
                // Cria um novo registro e salva no banco de dados
                IMCRegistro registro = new IMCRegistro(nome, peso, alturaEmMetros, imc, classificacao);
                IMCRegistroDAO.salvar(registro); // Chama o método salvar
            } else {
                System.out.println("Nome não pode estar vazio.");
            }
        } else {
            System.out.println("Peso e altura devem ser maiores que zero.");
        }
    }

    private String getClassificacao(double imc) {
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 24.9) return "Peso Normal";
        if (imc < 29.9) return "Sobrepeso";
        if (imc < 34.9) return "Obesidade I";
        if (imc < 39.9) return "Obesidade II";
        return "Obesidade III";
    }

    @FXML
    public void btArm(ActionEvent actionEvent) {
        calcularIMC();
    }

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
}
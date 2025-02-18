package com.example.sla;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculoIMCController implements Initializable {

    public Slider slPeso;
    public Slider slAlt;
    public Label altL;
    public Label kiloL;
    public Text resultadoIMCText; // Text para mostrar o IMC
    public Label classificacaoIMC; // Label para a classificação do IMC

    private int Kilo;
    private int alt;

    // Método para calcular o IMC
    private void calcularIMC() {
        if (Kilo > 0 && alt > 0) {
            double alturaEmMetros = alt / 100.0; // Converte a altura para metros
            double imc = Kilo / (alturaEmMetros * alturaEmMetros); // Calcula o IMC

            // Exibe o valor do IMC com duas casas decimais
            resultadoIMCText.setText(String.format("%.2f", imc));

            // Classificação do IMC
            String classificacao = "";
            if (imc < 18.5) {
                classificacao = "Abaixo do peso";
            } else if (imc >= 18.5 && imc < 24.9) {
                classificacao = "Peso Normal";
            } else if (imc >= 25 && imc < 29.9) {
                classificacao = "Sobrepeso";
            } else if (imc >= 30 && imc < 34.9) {
                classificacao = "Obesidade I";
            } else if (imc >= 35 && imc < 39.9) {
                classificacao = "Obesidade II";
            } else {
                classificacao = "Obesidade III";
            }

            // Atualiza o Label de classificação com o resultado
            classificacaoIMC.setText(classificacao);
        }
    }

    public void btArm(ActionEvent actionEvent) {
        // Chama o método de calcular IMC quando o botão for clicado
        calcularIMC();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Ouvinte para o Slider de Peso
        slPeso.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Atualiza o valor do peso
                Kilo = (int) slPeso.getValue();
                kiloL.setText(Integer.toString(Kilo) + " KG"); // Atualiza o Label com o novo valor
            }
        });

        // Ouvinte para o Slider de Altura
        slAlt.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Atualiza a altura, com arredondamento para o valor inteiro
                alt = (int) Math.round(slAlt.getValue());
                altL.setText(Integer.toString(alt) + " CM"); // Atualiza o Label com a nova altura
            }
        });

        // Inicializa as labels com valores padrões
        kiloL.setText("0 KG");
        altL.setText("75 CM");
        resultadoIMCText.setText("0.00");
        classificacaoIMC.setText(""); // Inicializa com o texto vazio
    }
}

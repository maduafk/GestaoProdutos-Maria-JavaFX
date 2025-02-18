package com.example.sla;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PainelHistoricoController {

    @FXML
    private Label mensagemLabel;

    public void initialize() {
        mensagemLabel.setText("Ops!! Essa página ainda não existe :(");
    }
}

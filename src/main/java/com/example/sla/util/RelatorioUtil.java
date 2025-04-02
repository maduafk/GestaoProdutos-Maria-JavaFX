package com.example.sla.util;

import com.example.sla.Entity.Produto;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class RelatorioUtil {

    public static List<Produto> getEstoqueBaixo(List<Produto> produtos) {
        return produtos.stream()
                .filter(p -> p.getQuantidade() < 10)
                .collect(Collectors.toList());
    }

    public static BigDecimal getValorTotalEstoque(List<Produto> produtos) {
        return produtos.stream()
                .map(p -> p.getPreco().multiply(BigDecimal.valueOf(p.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static void salvarRelatorioEstoqueBaixo(List<Produto> produtos, String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("Código,Nome,Quantidade,Preço");
            produtos.forEach(p -> writer.println(
                    p.getCodigo() + "," +
                            p.getNome() + "," +
                            p.getQuantidade() + "," +
                            p.getPreco()
            ));
        } catch (IOException e) {
            System.err.println("Erro ao salvar relatório: " + e.getMessage());
        }
    }
}
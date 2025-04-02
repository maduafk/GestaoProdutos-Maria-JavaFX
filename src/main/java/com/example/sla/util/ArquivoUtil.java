package com.example.sla.util;

import com.example.sla.Entity.Produto;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtil {

    private static final String ARQUIVO_PRODUTOS = "produtos.txt";

    public static List<Produto> carregarProdutos() {
        List<Produto> produtos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_PRODUTOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 4) {
                    Produto p = new Produto();
                    p.setCodigo(dados[0]);
                    p.setNome(dados[1]);
                    p.setQuantidade(Integer.parseInt(dados[2]));
                    p.setPreco(new BigDecimal(dados[3]));
                    produtos.add(p);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar produtos: " + e.getMessage());
        }

        return produtos;
    }

    public static void salvarProdutos(List<Produto> produtos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_PRODUTOS))) {
            for (Produto p : produtos) {
                writer.println(String.format("%s,%s,%d,%.2f",
                        p.getCodigo(),
                        p.getNome(),
                        p.getQuantidade(),
                        p.getPreco()));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }
}
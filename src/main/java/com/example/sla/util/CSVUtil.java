package com.example.sla.util;

import com.example.sla.Entity.Produto;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    private static final String ARQUIVO_PRODUTOS = "produtos.csv";

    public static List<Produto> carregarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        File arquivo = new File(ARQUIVO_PRODUTOS);

        if (!arquivo.exists()) {
            return produtos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 4) {
                    Produto p = new Produto(
                            dados[0],
                            dados[1],
                            new BigDecimal(dados[2]),
                            Integer.parseInt(dados[3])
                    );
                    produtos.add(p);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar produtos: " + e.getMessage());
        }
        return produtos;
    }

    public static void salvarProduto(Produto produto) {
        List<Produto> produtos = carregarProdutos();
        produtos.add(produto);
        salvarTodosProdutos(produtos);
    }

    public static void removerProduto(Produto produto) {
        List<Produto> produtos = carregarProdutos();
        produtos.removeIf(p -> p.getCodigo().equals(produto.getCodigo()));
        salvarTodosProdutos(produtos);
    }

    public static void salvarTodosProdutos(List<Produto> produtos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_PRODUTOS))) {
            for (Produto p : produtos) {
                writer.println(String.join(",",
                        p.getCodigo(),
                        p.getNome(),
                        p.getPreco().toString(),
                        String.valueOf(p.getQuantidade())));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }
}
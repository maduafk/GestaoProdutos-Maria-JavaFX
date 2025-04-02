package javaio;

import com.example.sla.Entity.Produto;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtil {
    public static void salvarDadosCSV(List<Produto> produtos, String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("codigo,nome,preco,quantidade\n");
            for (Produto p : produtos) {
                writer.write(p.getCodigo() + "," +
                        p.getNome() + "," +
                        p.getPreco().toString() + "," +
                        p.getQuantidade() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Produto> carregarDadosCSV(String nomeArquivo) {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            reader.readLine(); // Pula cabeçalho
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                Produto p = new Produto(
                        dados[0],
                        dados[1],
                        new BigDecimal(dados[2]), // Corrigido: cria BigDecimal diretamente da String
                        Integer.parseInt(dados[3])
                );
                produtos.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return produtos;
    }
}
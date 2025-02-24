package sla.java.io;

import com.example.sla.IMCRegistro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtil {

    // Método para salvar os dados em um arquivo CSV
    public static void salvarDadosCSV(List<IMCRegistro> registros, String nomeArquivo) {
        // Salva no diretório do projeto
        salvarArquivo(registros, nomeArquivo);

        // Salva na pasta Downloads do usuário
        String caminhoDownloads = System.getProperty("user.home") + "/Downloads/" + nomeArquivo;
        salvarArquivo(registros, caminhoDownloads);
    }

    // Método auxiliar para salvar os dados em um arquivo
    private static void salvarArquivo(List<IMCRegistro> registros, String caminhoArquivo) {
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            // Escreve o cabeçalho do CSV
            writer.write("nome,peso,altura,imc,classificacao\n");

            // Escreve os dados de cada registro
            for (IMCRegistro registro : registros) {
                writer.write(registro.getNome() + "," +
                        registro.getPeso() + "," +
                        registro.getAltura() + "," +
                        registro.getImc() + "," +
                        registro.getClassificacao() + "\n");
            }

            System.out.println("Dados salvos com sucesso em " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
    }

    // Método para carregar os dados de um arquivo CSV
    public static List<IMCRegistro> carregarDadosCSV(String nomeArquivo) {
        List<IMCRegistro> registros = new ArrayList<>();

        // Tenta carregar do diretório do projeto
        if (!carregarArquivo(registros, nomeArquivo)) {
            // Se não encontrar no projeto, tenta carregar da pasta Downloads
            String caminhoDownloads = System.getProperty("user.home") + "/Downloads/" + nomeArquivo;
            carregarArquivo(registros, caminhoDownloads);
        }

        return registros;
    }

    // Método auxiliar para carregar os dados de um arquivo
    private static boolean carregarArquivo(List<IMCRegistro> registros, String caminhoArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            // Ignora o cabeçalho do CSV
            reader.readLine();

            // Lê cada linha do arquivo
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(","); // Divide a linha em campos

                // Cria um novo registro com os dados lidos
                String nome = dados[0];
                int peso = Integer.parseInt(dados[1]);
                double altura = Double.parseDouble(dados[2]);
                double imc = Double.parseDouble(dados[3]);
                String classificacao = dados[4];

                IMCRegistro registro = new IMCRegistro(nome, peso, altura, imc, classificacao);
                registros.add(registro);
            }

            System.out.println("Dados carregados com sucesso de " + caminhoArquivo);
            return true; // Arquivo carregado com sucesso
        } catch (IOException e) {
            System.err.println("Erro ao carregar os dados do arquivo: " + caminhoArquivo);
            return false; // Arquivo não encontrado ou erro ao ler
        }
    }
}
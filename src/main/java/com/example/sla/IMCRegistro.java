package com.example.sla;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "imc_registro") // Nome da tabela no banco de dados
public class IMCRegistro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID
    private Long id;

    @Column(name = "nome", nullable = false) // Coluna "nome" no banco de dados
    private String nome;

    @Column(name = "peso", nullable = false) // Coluna "peso" no banco de dados
    private int peso;

    @Column(name = "altura", nullable = false) // Coluna "altura" no banco de dados
    private double altura;

    @Column(name = "imc", nullable = false) // Coluna "imc" no banco de dados
    private double imc;

    @Column(name = "classificacao", nullable = false) // Coluna "classificacao" no banco de dados
    private String classificacao;

    // Construtor padrão (obrigatório para JPA)
    public IMCRegistro() {}

    // Construtor com parâmetros
    public IMCRegistro(String nome, int peso, double altura, double imc, String classificacao) {
        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
        this.classificacao = classificacao;
    }

    // Getters e Setters (obrigatórios para JPA)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getPeso() { return peso; }
    public void setPeso(int peso) { this.peso = peso; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

    public double getImc() { return imc; }
    public void setImc(double imc) { this.imc = imc; }

    public String getClassificacao() { return classificacao; }
    public void setClassificacao(String classificacao) { this.classificacao = classificacao; }

    @Override
    public String toString() {
        return "IMCRegistro{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", peso=" + peso +
                ", altura=" + altura +
                ", imc=" + imc +
                ", classificacao='" + classificacao + '\'' +
                '}';
    }
}
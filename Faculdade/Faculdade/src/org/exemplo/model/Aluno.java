package org.exemplo.model;

import java.time.LocalDate;

public class Aluno {
    private int id;
    private int matricula;
    private String nome;
    private String telefone;
    private LocalDate dataDeNascimento;
    private String curso;
    private String cpf;

    public Aluno(int matricula, String nome, String telefone, LocalDate dataDeNascimento, String curso, String cpf) {
        this.matricula = matricula;
        this.nome = nome;
        this.telefone = telefone;
        this.dataDeNascimento = dataDeNascimento;
        this.curso = curso;
        this.cpf = cpf;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMatricula() { return matricula; }
    public void setMatricula(int matricula) { this.matricula = matricula; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public LocalDate getDataDeNascimento() { return dataDeNascimento; }
    public void setDataDeNascimento(LocalDate dataDeNascimento) { this.dataDeNascimento = dataDeNascimento; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", matricula=" + matricula +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dataDeNascimento=" + dataDeNascimento +
                ", curso='" + curso + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}


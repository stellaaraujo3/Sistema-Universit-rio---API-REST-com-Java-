package org.exemplo;

public class Cursos {
    private int id;
    private String nome;

    public Cursos(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Cursos(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
package org.exemplo.model;

public class Turma {
    private int idTurma;
    private String nomeTurma;
    private String turno;
    private String curso;

    public Turma() {}

    public Turma(String nomeTurma, String turno, String curso) {
        this.nomeTurma = nomeTurma;
        this.turno = turno;
        this.curso = curso;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return idTurma + ": " + nomeTurma + " - " + turno + " - " + curso;
    }
}
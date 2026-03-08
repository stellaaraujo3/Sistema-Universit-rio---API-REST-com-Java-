package org.exemplo.model;

public class Usuario {
    private int idusuario;
    private String usuario;
    private String senha;
    private String tipousuario;
    private int alunomatricula;

    public Usuario(int idusuario, String usuario, String senha, String tipousuario, int alunomatricula) {
        this.idusuario = idusuario;
        this.usuario = usuario;
        this.senha = senha;
        this.tipousuario = tipousuario;
        this.alunomatricula = alunomatricula;
    }

    public Usuario(String usuario, String senha, String tipousuario, int alunomatricula) {
        this.usuario = usuario;
        this.senha = senha;
        this.tipousuario = tipousuario;
        this.alunomatricula = alunomatricula;
    }

    public Usuario(int idusuario, String usuario, String senha, String tipousuario, Object o, int alunomatricula, Object o1) {
    }

    public int getidusuario() {
        return idusuario;
    }

    public String getusuario() {
        return usuario;
    }

    public String getsenha() {
        return senha;
    }

    public String gettipousuario() {
        return tipousuario;
    }

    public int getalunomatricula() {
        return alunomatricula;
    }

    @Override
    public String toString() {
        return "ID: " + idusuario +
                ", Usuário: " + usuario +
                ", Tipo: " + tipousuario +
                ", Matrícula Aluno: " + alunomatricula;
    }
}


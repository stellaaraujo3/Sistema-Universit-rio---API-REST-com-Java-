package org.exemplo;

public class LoginRequest {
    public String usuario;
    public String senha;

    public LoginRequest(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public LoginRequest(String loginRealizadoComSucesso, String getusuario, String gettipousuario) {
    }
}

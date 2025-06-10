package org.exemplo;

public class LoginResponse {
    public String mensagem;
    public String usuario;
    public String tipoUsuario;

    public LoginResponse(String mensagem, String usuario, String tipoUsuario) {
        this.mensagem = mensagem;
        this.usuario = usuario;
        this.tipoUsuario = tipoUsuario;
    }
}

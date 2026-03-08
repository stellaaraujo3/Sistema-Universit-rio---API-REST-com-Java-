package org.exemplo.dao;

import org.exemplo.config.ConnectFactory;
import org.exemplo.model.Aluno;
import org.exemplo.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final Connection conexao;


    public UsuarioDAO() {
        this.conexao = ConnectFactory.getConnection();

    }

    //Tabela usuarios possui o campo aluno_matricula e foi definido como uma chave estrangeira (FK).
    public void createTable() {

        String sql = """
                 CREATE TABLE IF NOT EXISTS usuario  (
                     idusuario SERIAL PRIMARY KEY,
                     usuario VARCHAR(50) UNIQUE NOT NULL,
                     senha VARCHAR(255) NOT NULL,
                     tipousuario VARCHAR(25) NOT NULL CHECK (tipousuario IN ('usuario', 'adm')),
                     alunomatricula INTEGER,
                     FOREIGN KEY (alunomatricula) REFERENCES alunos(matricula) 
                );
                """;
    }


    // METODO PARA VERIFICAR SE UM USUARIO ESTA CADASTRADO NA LISTA DE USUARIOS
    public Usuario login(String username, String senha) {

        String sql = "SELECT * FROM usuario WHERE usuario = ? AND senha = ?";

        try (Connection conn = ConnectFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Usuario(
                        rs.getInt("idusuario"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getString("tipousuario"),
                        rs.getInt("alunomatricula")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro no login:");
            e.printStackTrace();
        }

        return null;
    }

    // METODO PARA ADICIONAR UM NOVO USUARIO
    public void adicionarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario(usuario,senha,tipousuario,alunomatricula ) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getusuario());
            stmt.setString(2, usuario.getsenha());
            stmt.setString(3, usuario.gettipousuario());

            if (usuario.gettipousuario().equalsIgnoreCase("adm")) {
                stmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(4, usuario.getalunomatricula());
            }

            stmt.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuário: " + e.getMessage());
        }
    }

    // METODO PARA LISTAS OS USUARIOS CADASTRADOS
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = ConnectFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("idusuario"),
                        rs.getString("usuario"),
                        rs.getString("senha"),
                        rs.getString("tipousuario"),
                        rs.getInt("alunomatricula")
                );
                usuarios.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários:");
            e.printStackTrace();
        }

        return usuarios;
    }


    //METODO PARA ATUALIZAR O USUARIO
    public void atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET usuario = ?, senha = ?, alunomatricula = ?, tipousuario = ? WHERE idusuario = ?";

        try (Connection conn = ConnectFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getusuario());
            stmt.setString(2, usuario.getsenha());

            if (usuario.gettipousuario().equalsIgnoreCase("adm")) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, usuario.getalunomatricula());
            }

            stmt.setString(4, usuario.gettipousuario());
            stmt.setInt(5, usuario.getidusuario());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    //METODO PARA REMOVER UM USUARIO
    public void removerUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE idusuario = ?";

        try (PreparedStatement stmt = this.conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao remover usuario:");
            e.printStackTrace();

        }
    }

}
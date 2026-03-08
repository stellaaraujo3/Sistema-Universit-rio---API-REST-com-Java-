package org.exemplo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursosDAO {
    private final Connection conexao;

    public CursosDAO() {
        this.conexao = ConnectFactory.getConnection();
    }

    // Método para criar a tabela de cursos
    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS curso (
                id_curso SERIAL PRIMARY KEY,
                nome VARCHAR(200) UNIQUE NOT NULL
            );
        """;
        try (Statement stmt = conexao.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar se o curso já existe
    public boolean cursoExiste(String nomeCurso) {
        String sql = "SELECT COUNT(*) FROM curso WHERE nome = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeCurso);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se já houver um curso com o nome fornecido
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false se o curso não existir
    }

    // Método para inserir um novo curso
    public void inserirCurso(Cursos curso) {
        if (cursoExiste(curso.getNome())) {
            System.out.println("Erro: Curso já existe.");
            return; // Não permite a inserção do curso se já existir
        }

        String sql = "INSERT INTO curso (nome) VALUES (?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.executeUpdate();
            System.out.println("Curso cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos os cursos
    public List<Cursos> listarCursos() {
        List<Cursos> cursos = new ArrayList<>();
        String sql = "SELECT * FROM curso ORDER BY nome";

        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cursos.add(new Cursos(rs.getInt("id_curso"), rs.getString("nome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursos;
    }

    // Método para buscar o ID do curso pelo nome
    public int buscarIdPorNome(String nome) {
        String sql = "SELECT id_curso FROM curso WHERE nome = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_curso");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 se o curso não for encontrado
    }
}
package org.exemplo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {
    private final Connection conexao;

    public TurmaDAO() {
        conexao = ConnectFactory.getConnection();
    }
    // FORAM CRIADAS DUAS TABELAS SQL UMA DE TURMA E UMA RELACIONANDO O ALUNO A TURMA
    public void createTable() {
        String sql1 = """
            CREATE TABLE IF NOT EXISTS turma (
                id_turma SERIAL PRIMARY KEY,
                nome_turma VARCHAR(100) NOT NULL,
                turno VARCHAR(20) NOT NULL,
                curso VARCHAR(100) NOT NULL
            );
        """;

        String sql2 = """
            CREATE TABLE IF NOT EXISTS aluno_turma (
                id SERIAL PRIMARY KEY,
                aluno_matricula INT NOT NULL,
                id_turma INT NOT NULL,
                FOREIGN KEY (aluno_matricula) REFERENCES alunos(matricula),
                FOREIGN KEY (id_turma) REFERENCES turma(id_turma),
                UNIQUE (aluno_matricula, id_turma)
            );
        """;

    }

    public void criarTurma(Turma turma) {
        String sql = "INSERT INTO turma (nome_turma, turno, curso) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, turma.getNomeTurma());
            stmt.setString(2, turma.getTurno());
            stmt.setString(3, turma.getCurso());
            stmt.executeUpdate();
            System.out.println("Turma criada com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void matricularAlunoEmTurma(int matricula, int turmaId) {

    }
    // METODO USADO PARA LISTAR AS TURMAS CADASTRADAS
    public List<Turma> listarTurmas() {
        List<Turma> turmas = new ArrayList<>();
        String sql = "SELECT * FROM turma";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Turma turma = new Turma();
                turma.setIdTurma(rs.getInt("id_turma"));
                turma.setNomeTurma(rs.getString("nome_turma"));
                turma.setTurno(rs.getString("turno"));
                turma.setCurso(rs.getString("curso"));
                turmas.add(turma);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turmas;
    }
    //METODO PARA  REMOVER ALUNO PELO ID
    public void removerTurmaPorId(int id) {
        String sql = "DELETE FROM turma WHERE id_turma = ?";
        try (Connection conn = ConnectFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Turma removida com sucesso.");
            } else {
                System.out.println("Turma com ID " + id + " não encontrada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover turma: " + e.getMessage());
        }
    }
}
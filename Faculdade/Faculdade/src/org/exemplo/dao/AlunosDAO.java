package org.exemplo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunosDAO {
    private final Connection conexao;

    public AlunosDAO() {
        this.conexao = ConnectFactory.getConnection();
    }

    //tabela alunos sql
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS alunos (
                    id SERIAL PRIMARY KEY ,
                    matricula INT UNIQUE NOT NULL,
                    nome VARCHAR(255) NOT NULL,
                    telefone VARCHAR(25),
                    data_de_nascimento DATE NOT NULL,
                    curso VARCHAR(100) NOT NULL,
                    cpf VARCHAR(15) NOT NULL
                );
                """;

    }

    //metodo usado para iserir alunos no sistema.
    public void inserirAluno(Aluno aluno) {
        String sql = "INSERT INTO alunos (matricula, nome, telefone, data_de_nascimento, curso, cpf) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = this.conexao.prepareStatement(sql)) {
            stmt.setInt(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getTelefone());
            stmt.setDate(4, Date.valueOf(aluno.getDataDeNascimento()));
            stmt.setString(5, aluno.getCurso());
            stmt.setString(6, aluno.getCpf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir aluno:");
            e.printStackTrace();
        }
    }

    //metodos usados para listar alunos cadastrados.
    public List<Aluno> listarAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos";

        try (Statement stmt = this.conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("matricula"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getDate("data_de_nascimento").toLocalDate(),
                        rs.getString("curso"),
                        rs.getString("cpf")
                );
                aluno.setId(rs.getInt("id"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos:");
            e.printStackTrace();
        }
        return alunos;
    }

    //metodo usado para buscar aluno pela matricula.
    public Aluno buscarPorMatricula(int matricula) {
        String sql = "SELECT * FROM alunos WHERE matricula = ?";

        try (PreparedStatement stmt = this.conexao.prepareStatement(sql)) {
            stmt.setInt(1, matricula);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("matricula"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getDate("data_de_nascimento").toLocalDate(),
                        rs.getString("curso"),
                        rs.getString("cpf")
                );
                aluno.setId(rs.getInt("id"));
                return aluno;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar aluno por matrícula:");
            e.printStackTrace();
        }
        return null;
    }

    //metodo para buscar aluno pelo nome.
    public List<Aluno> buscarPorNome(String nome) {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos WHERE nome ILIKE ?";

        try (PreparedStatement stmt = this.conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("matricula"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getDate("data_de_nascimento").toLocalDate(),
                        rs.getString("curso"),
                        rs.getString("cpf")
                );
                aluno.setId(rs.getInt("id"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar aluno por nome:");
            e.printStackTrace();
        }
        return alunos;
    }

    //metodo para atualizar o telefone dos alunos cadastrados.
    public void atualizarTelefone(int matricula, String novoTelefone) {
        String sql = "UPDATE alunos SET telefone = ? WHERE matricula = ?";

        try (PreparedStatement stmt = this.conexao.prepareStatement(sql)) {
            stmt.setString(1, novoTelefone);
            stmt.setInt(2, matricula);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar telefone:");
            e.printStackTrace();
        }
    }

    //metodo usado para remover aluno do sistema
    public void removerAluno(int matricula) {
        String sql = "DELETE FROM alunos WHERE matricula = ?";

        try (PreparedStatement stmt = this.conexao.prepareStatement(sql)) {
            stmt.setInt(1, matricula);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao remover aluno:");
            e.printStackTrace();
        }
    }
}
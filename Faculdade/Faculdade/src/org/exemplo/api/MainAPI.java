package org.exemplo.api;

import static spark.Spark.*;
import com.google.gson.Gson;
import org.exemplo.dao.AlunosDAO;
import org.exemplo.dao.UsuarioDAO;
import org.exemplo.dto.LoginRequest;
import org.exemplo.model.Aluno;
import org.exemplo.model.Usuario;

import java.util.Map;

public class MainAPI {

    public static void main(String[] args) {

        port(4567);

        // CORS
        options("/*", (request, response) -> {

            String headers = request.headers("Access-Control-Request-Headers");
            if (headers != null) {
                response.header("Access-Control-Allow-Headers", headers);
            }

            String methods = request.headers("Access-Control-Request-Method");
            if (methods != null) {
                response.header("Access-Control-Allow-Methods", methods);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });

        Gson gson = new Gson();
        AlunosDAO alunosDAO = new AlunosDAO();

        // LOGIN ADMIN
        post("/login-adm", (req, res) -> {

            LoginRequest loginRequest = gson.fromJson(req.body(), LoginRequest.class);

            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuario = dao.login(loginRequest.usuario, loginRequest.senha);

            if (usuario != null && "adm".equalsIgnoreCase(usuario.gettipousuario())) {

                res.status(200);

                return gson.toJson(Map.of(
                        "mensagem", "Login realizado com sucesso",
                        "usuario", usuario.getusuario(),
                        "tipo", usuario.gettipousuario()
                ));

            } else {

                res.status(401);

                return gson.toJson(Map.of(
                        "erro", "Usuário ou senha inválidos"
                ));
            }
        });

        // LOGIN ALUNO
        post("/login-aluno", (req, res) -> {

            LoginRequest loginRequest = gson.fromJson(req.body(), LoginRequest.class);

            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuario = dao.login(loginRequest.usuario, loginRequest.senha);

            if (usuario != null && "usuario".equalsIgnoreCase(usuario.gettipousuario())) {

                Aluno aluno = alunosDAO.buscarPorMatricula(usuario.getalunomatricula());

                res.status(200);

                return gson.toJson(Map.of(
                        "mensagem", "Login realizado com sucesso",
                        "nome", aluno.getNome(),
                        "matricula", aluno.getMatricula(),
                        "curso", aluno.getCurso(),
                        "telefone", aluno.getTelefone(),
                        "cpf", aluno.getCpf()
                ));

            } else {

                res.status(401);

                return gson.toJson(Map.of(
                        "erro", "Usuário ou senha inválidos"
                ));
            }
        });

        // CADASTRAR ALUNO
        post("/admin/inserir_aluno", (req, res) -> {

            try {

                Aluno novoAluno = gson.fromJson(req.body(), Aluno.class);

                alunosDAO.inserirAluno(novoAluno);

                res.status(201);

                return gson.toJson(novoAluno);

            } catch (Exception e) {

                res.status(500);

                return gson.toJson(Map.of(
                        "erro", "Erro ao cadastrar aluno: " + e.getMessage()
                ));
            }
        });

        // LISTAR ALUNOS
        get("/alunos", (req, res) -> {

            return gson.toJson(alunosDAO.listarAlunos());

        });

    }
}
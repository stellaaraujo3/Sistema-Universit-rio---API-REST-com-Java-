package org.exemplo;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.util.Map;

public class MainAPI {
    public static void main(String[] args) {

        // Porta padrão
        port(4567);

        // ⚠️ Só se você for usar arquivos estáticos pelo Spark
        // Para VS Code frontend, isso pode ser ignorado
        // staticFiles.location("/public");

        // 🟢 Libera CORS para o frontend acessar a API
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
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

        //  Rota de teste
        get("/", (req, res) -> "{\"mensagem\":\"API funcionando!\"}");

        //  Rota de login de administrador
        post("/login_adm/adm", (req, res) -> {
            LoginRequest loginRequest = gson.fromJson(req.body(), LoginRequest.class);

            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuario = dao.login(loginRequest.usuario, loginRequest.senha);

            if (usuario != null && "adm".equalsIgnoreCase(usuario.gettipousuario())) {
                res.status(200);
                return gson.toJson(new LoginRequest("Login realizado com sucesso", usuario.getusuario(), usuario.gettipousuario()));
            } else {
                res.status(401);
                return gson.toJson(new LoginRequest("Usuário ou senha inválidos", null, null));
            }
        });

        //  Rota para cadastrar aluno (exemplo)
        post("admin/inserir_aluno", (req, res) -> {
            try {
                Aluno novoAluno = gson.fromJson(req.body(), Aluno.class);
                alunosDAO.inserirAluno(novoAluno);
                return gson.toJson(novoAluno);
            } catch (Exception e) {
                res.status(500);
                return gson.toJson(Map.of("erro", "Erro ao cadastrar aluno: " + e.getMessage()));
            }
        });

    }
}

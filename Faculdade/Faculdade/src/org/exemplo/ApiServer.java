package org.exemplo;

import static spark.Spark.*;

public class ApiServer {

    public static void iniciarServidor() {
        port(4567); // Porta padrão do Spark

        // Configurações globais
        before((req, res) -> res.type("application/json"));

        // Tratamento de erros genéricos
        exception(Exception.class, (e, req, res) -> {
            res.status(500);
            res.body("{\"error\":\"" + e.getMessage() + "\"}");
            e.printStackTrace();
        });

        // Rota raiz simples
        get("/", (req, res) -> "{\"message\":\"API Universit funcionando!\"}");
    }
}

document.getElementById("login-form").addEventListener("submit", function(e) {
    e.preventDefault(); // Impede o recarregamento da página

    const usuario = document.getElementById("username").value.trim();
    const senha = document.getElementById("password").value.trim();

    const dadosLogin = {
        usuario: usuario,
        senha: senha
    };

    fetch("http://localhost:4567/login_adm/adm", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(dadosLogin)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Usuário ou senha inválidos");
        }
        return response.json();
    })
    .then(data => {
        alert(data.mensagem || "Login realizado com sucesso!");

        // Redireciona para o menu do administrador, por exemplo:
        window.location.href = "/html/admin.html";
    })
    .catch(error => {
        alert("Erro no login: " + error.message);
    });
});

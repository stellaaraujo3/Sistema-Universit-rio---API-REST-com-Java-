document.getElementById("login-form").addEventListener("submit", function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const senha = document.getElementById("password").value;

    fetch("http://localhost:4567/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            senha: senha
        })
    })
    .then(response => {
        if (!response.ok) throw new Error("Falha no login");
        return response.json();
    })
    .then(data => {
        alert("Login feito com sucesso!");
        console.log(data);
    })
    .catch(error => {
        alert("Erro: " + error.message);
    });
});
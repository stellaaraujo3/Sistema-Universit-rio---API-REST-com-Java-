document.getElementById("login-form").addEventListener("submit", function (event) {
    event.preventDefault();

    const usuario = document.getElementById("username").value;
    const senha = document.getElementById("password").value;

    fetch("http://localhost:4567/login-aluno", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            usuario: usuario,
            senha: senha
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Usuário ou senha inválidos");
        }
        return response.json();
    })
    .then(data => {

    localStorage.setItem("aluno", JSON.stringify(data));

    window.location.href = "/html-aluno/aluno.html";

})
    .catch(error => {
        alert(error.message);
    });
});
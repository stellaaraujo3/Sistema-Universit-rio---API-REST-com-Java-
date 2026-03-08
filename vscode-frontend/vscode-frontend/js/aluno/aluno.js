document.getElementById("loginForm").addEventListener("submit", async function(event) {

  event.preventDefault();

  const cpf = document.getElementById("cpf").value.trim();
  const senha = document.getElementById("senha").value.trim();

  try {

    const response = await fetch("http://localhost:4567/login-aluno", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        cpf: cpf,
        senha: senha
      })
    });

    if (!response.ok) {
      throw new Error("CPF ou senha inválidos");
    }

    const aluno = await response.json();

    // 🔥 salva o aluno no navegador
    localStorage.setItem("aluno", JSON.stringify(aluno));

    // 🔥 vai para o painel do aluno
    window.location.href = "/html-aluno/aluno.html";

  } catch (error) {

    alert("Erro no login: " + error.message);

  }

});
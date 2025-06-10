document.getElementById("loginForm").addEventListener("submit", async function(event) {
  event.preventDefault();

  const cpf = document.getElementById("cpf").value;
  const senha = document.getElementById("senha").value;

  try {
    const response = await fetch("http://localhost:4567/login-aluno",{ 
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ cpf, senha })
    });

    if (!response.ok) {
      throw new Error("CPF ou senha inválidos");
    }

    const aluno = await response.json();

    // Exibindo os dados do aluno
    document.getElementById("resultado").innerHTML = `
      <p><strong>Nome:</strong> ${aluno.nome}</p>
      <p><strong>Matrícula:</strong> ${aluno.matricula}</p>
      <p><strong>Curso:</strong> ${aluno.curso}</p>
      <p><strong>Telefone:</strong> ${aluno.telefone}</p>
      <p><strong>CPF:</strong> ${aluno.cpf}</p>
    `;
  } catch (error) {
    alert("Erro no login: " + error.message);
  }
});

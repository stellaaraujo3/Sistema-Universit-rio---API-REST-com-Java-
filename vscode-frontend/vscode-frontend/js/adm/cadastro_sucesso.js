window.addEventListener("DOMContentLoaded", () => {
  const aluno = JSON.parse(localStorage.getItem("alunoCadastrado"));

  if (!aluno) {
    // Se não tiver dados, redireciona para o formulário de cadastro
    window.location.href = "/html/inserir_aluno.html";
    return;
  }

  // Preenche os campos no HTML
  document.getElementById("matricula").textContent = aluno.matricula || "N/A";
  document.getElementById("nome").textContent = aluno.nome || "";
  document.getElementById("cpf").textContent = aluno.cpf || "";
  document.getElementById("telefone").textContent = aluno.telefone || "";
  document.getElementById("dataNascimento").textContent = aluno.dataDeNascimento || "";
  document.getElementById("curso").textContent = aluno.curso || "";

  // Limpa o localStorage para evitar que os dados fiquem salvos indefinidamente
  localStorage.removeItem("alunoCadastrado");
});

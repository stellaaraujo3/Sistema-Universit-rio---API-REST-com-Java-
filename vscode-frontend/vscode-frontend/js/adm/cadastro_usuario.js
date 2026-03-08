
// Verificar se todos os campos estão preenchidos
  if (!usuario || !senha) {
  document.getElementById("mensagem").textContent = "Por favor, preencha todos os campos.";
  document.getElementById("mensagem").style.color = "red";
  return;
}

// Função que será executada quando o tipo de usuário for alterado
document.getElementById("tipo").addEventListener("change", function () {
  const tipo = this.value;
  const matriculaInput = document.getElementById("matricula");

  // Se for admin, desabilita a matrícula
  if (tipo === "admin") {
    matriculaInput.disabled = true;
    matriculaInput.value = "";  // Limpa o campo matrícula
  } else if (tipo === "aluno") {
    matriculaInput.disabled = false;  // Habilita o campo matrícula
  }
});

// Função para lidar com o envio do formulário
document.getElementById("formUsuario").addEventListener("submit", function (event) {
  event.preventDefault();  // Evita o envio do formulário sem tratar os dados

  // Coletando os dados do formulário
  const usuario = document.getElementById("usuario").value;
  const senha = document.getElementById("senha").value;
  const tipo = document.getElementById("tipo").value;
  let matricula = document.getElementById("matricula").value;

  // Se for admin, a matrícula deve ser nula
  if (tipo === "admin") {
    matricula = null;
  }

  // Validação simples para garantir que os campos obrigatórios foram preenchidos
  if (!usuario || !senha || !tipo) {
    alert("Por favor, preencha todos os campos obrigatórios.");
    return;
  }

  // Dados que serão enviados para o backend
  const usuarioData = {
    usuario: usuario,
    senha: senha,
    tipo: tipo,
    matricula: matricula
  };

  // Enviando os dados para a API via Fetch
  fetch('/api/adicionar_usuario', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(usuarioData), // Converte o objeto para JSON
  })
    .then(response => response.json()) // Converte a resposta para JSON
    .then(data => {
      if (data.success) {
        alert("Usuário cadastrado com sucesso!");
        document.getElementById("formUsuario").reset();  // Limpa o formulário após o sucesso
      } else {
        alert("Erro ao cadastrar o usuário.");
      }
    })
    .catch(error => {
      console.error("Erro ao enviar os dados:", error);
      alert("Ocorreu um erro ao cadastrar o usuário.");
    });
});



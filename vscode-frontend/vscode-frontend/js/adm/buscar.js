document.querySelector('#searchForm').addEventListener('submit', function (e) {
  e.preventDefault(); // Previne o envio tradicional do formulário
  
  // Obtendo os valores dos campos de matrícula e nome
  const matricula = document.querySelector('#matricula').value.trim();
  const nome = document.querySelector('#nome').value.trim();

  // Verificando qual campo foi preenchido
  if (matricula && nome) {
    alert('Por favor, preencha apenas um campo (Matrícula ou Nome).');
    return;
  }

  if (matricula) {
    // Chamar o método de busca por matrícula
    buscarPorMatricula(matricula);
  } else if (nome) {
    // Chamar o método de busca por nome
    buscarPorNome(nome);
  } else {
  }
});

// Função para buscar por matrícula
function buscarPorMatricula(matricula) {
  // Aqui você pode fazer uma requisição ao backend para buscar pela matrícula
  console.log(`Matrícula buscada: ${matricula}`);
  // Exemplo de requisição (usando fetch ou AJAX)
  // fetch(`/buscar/matricula/${matricula}`)
  //   .then(response => response.json())
  //   .then(data => console.log(data));
}

// Função para buscar por nome
function buscarPorNome(nome) {
  // Aqui você pode fazer uma requisição ao backend para buscar pelo nome
  console.log(`Nome buscado: ${nome}`);
  // Exemplo de requisição (usando fetch ou AJAX)
  // fetch(`/buscar/nome/${nome}`)
  //   .then(response => response.json())
  //   .then(data => console.log(data));
}

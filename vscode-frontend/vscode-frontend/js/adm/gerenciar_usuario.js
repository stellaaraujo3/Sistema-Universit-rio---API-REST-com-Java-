// Armazenando usuários em um array simples (isso poderia vir de um banco de dados em um sistema real)


// Selecionando os itens da lista
const listaUsuarios = document.querySelectorAll('');

// Função para listar usuários (exibe no console)
function listarUsuarios() {
  console.log("Usuários cadastrados:");
  usuarios.forEach(usuario => {
    console.log(usuario);
  });
}

// Função para adicionar um novo usuário
function adicionarUsuario() {
  const nome = prompt("Digite o nome do novo usuário:");
  if (nome && !usuarios.includes(nome)) {
    usuarios.push(nome);
    alert(`Usuário ${nome} adicionado com sucesso!`);
    listarUsuarios(); // Atualiza a lista no console
  } else if (usuarios.includes(nome)) {
    alert("Usuário já existe!");
  } else {
    alert("Nome inválido!");
  }
}

// Função para remover um usuário
function removerUsuario() {
  const nome = prompt("Digite o nome do usuário a ser removido:");
  const index = usuarios.indexOf(nome);
  
  if (index !== -1) {
    usuarios.splice(index, 1); // Remove o usuário do array
    alert(`Usuário ${nome} removido com sucesso!`);
    listarUsuarios(); // Atualiza a lista no console
  } else {
    alert("Usuário não encontrado!");
  }
}

// Adicionando eventos de clique aos itens da lista
listaUsuarios[0].addEventListener('click', adicionarUsuario); // Adicionar usuário
listaUsuarios[1].addEventListener('click', listarUsuarios);   // Listar usuários
listaUsuarios[2].addEventListener('click', removerUsuario);   // Remover usuário

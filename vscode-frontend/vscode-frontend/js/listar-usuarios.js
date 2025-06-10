// Função para listar os usuários
function listarUsuarios() {
  // Fazendo a requisição GET para a API de listar usuários
  fetch('/api/listar_usuarios')
    .then(response => {
      if (!response.ok) {
        throw new Error('Erro ao buscar usuários');
      }
      return response.json(); // Transformando a resposta em JSON
    })
    .then(data => {
      // Aqui assumimos que `data` é um array de usuários
      const usuarios = data;  // Dados da resposta da API
      const listaUsuarios = document.getElementById('lista-usuarios');  // Pegando o elemento UL

      // Limpa a lista antes de inserir novos dados
      listaUsuarios.innerHTML = '';

      // Preenchendo a lista com os dados dos usuários
      usuarios.forEach(usuario => {
        // Criando um novo item da lista <li> para cada usuário
        const li = document.createElement('li');

        // Adiciona as informações do usuário ao item da lista
        li.textContent = `Usuário: ${usuario.usuario}, Tipo: ${usuario.tipo}, Matrícula: ${usuario.matricula || 'N/A'}`;

        // Adiciona o item <li> à lista
        listaUsuarios.appendChild(li);
      });
    })
    .catch(error => {
      console.error('Erro ao listar usuários:', error);
      alert('Ocorreu um erro ao carregar a lista de usuários.');
    });
}

// Carrega a lista de usuários quando a página for carregada
document.addEventListener('DOMContentLoaded', listarUsuarios);

document.querySelector('form').addEventListener('submit', function (e) {
  e.preventDefault(); // Impede o envio tradicional do formulário
  
  // Obtendo o valor da matrícula
  const matricula = document.querySelector('input[type="text"]').value.trim();
  
  // Verifica se a matrícula foi fornecida
  if (!matricula) {
    alert('Por favor, informe uma matrícula válida.');
    return;
  }
  
  // Exemplo de como enviar a requisição para o backend (usando fetch)
  fetch(`/api/remover-aluno/${matricula}`, {  // Supondo que a URL do backend seja '/api/remover-aluno/:matricula'
    method: 'DELETE', // O método DELETE é usado para exclusão de dados
    headers: {
      'Content-Type': 'application/json',
    },
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Erro ao remover aluno');
    }
    return response.json(); // Ou response.text(), dependendo da resposta do servidor
  })
  .then(data => {
    alert(`Aluno com matrícula ${matricula} removido com sucesso!`);
    // Talvez você queira redirecionar para outra página ou limpar o formulário após a remoção
    document.querySelector('form').reset(); // Limpa o formulário
  })
  .catch(error => {
    alert(`Erro: ${error.message}`);
  });
});

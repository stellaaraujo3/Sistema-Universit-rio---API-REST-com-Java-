fetch("http://localhost:4567/alunos")
  .then(res => res.json())
  .then(alunos => {
    const ul = document.getElementById("lista-alunos");
    alunos.forEach(aluno => {
      const li = document.createElement("li");
      li.textContent = `Nome: ${aluno.nome} | Matrícula: ${aluno.matricula}`;
      ul.appendChild(li);
    });
  })
  .catch(err => {
    console.error("Erro ao carregar alunos:", err);
    alert("Erro ao buscar alunos");
  });
s
document.addEventListener("DOMContentLoaded", function () {
    const admBtn = document.querySelector('a[href="/html/login_adm.html"]');
    const alunoBtn = document.querySelector('a[href="/html-aluno/aluno.html"]');

    admBtn.addEventListener("click", function () {
      alert("Você escolheu login como Administrador.");
    });

    alunoBtn.addEventListener("click", function () {
      alert("Você escolheu login como Aluno.");
    });
  });
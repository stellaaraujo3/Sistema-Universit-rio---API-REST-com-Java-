document.getElementById("formAluno").addEventListener("submit", function(e) {
    e.preventDefault();

    const nome = document.getElementById("nome").value.trim();
    const cpf = document.getElementById("cpf").value.trim();
    const telefone = document.getElementById("telefone").value.trim();
    const dataNascimento = document.getElementById("dataNascimento").value;
    const curso = document.getElementById("curso").value.trim();

    if (!nome || !cpf || !telefone || !dataNascimento || !curso) {
        exibirMensagem("Por favor, preencha todos os campos.", "red");
        return;
    }

    if (!validarCPF(cpf)) {
        exibirMensagem("CPF inválido! Por favor, insira um CPF válido.", "red");
        return;
    }

    const aluno = {
        nome: nome,
        cpf: cpf.replace(/\D/g, ''), // Envia só números
        telefone: telefone.replace(/\D/g, ''),
        dataDeNascimento: dataNascimento, // formato yyyy-MM-dd
        curso: curso
    };

    fetch("http://localhost:4567/alunos", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(aluno)
    })
    .then(response => {
        if (!response.ok) throw new Error("Erro ao cadastrar aluno");
        return response.json();
    })
    .then(data => {
        localStorage.setItem("alunoCadastrado", JSON.stringify(data));
        exibirMensagem("Aluno cadastrado com sucesso!", "green");
        document.getElementById("formAluno").reset();
        setTimeout(() => {
            window.location.href = "/html/cadastro_sucesso.html";
        }, 1500);
    })
    .catch(err => {
        exibirMensagem("Erro: " + err.message, "red");
    });
});

function exibirMensagem(msg, cor) {
    const msgDiv = document.getElementById("mensagem");
    msgDiv.textContent = msg;
    msgDiv.style.color = cor;
}

// Validador de CPF 
function validarCPF(cpf) {
    cpf = cpf.replace(/[^\d]+/g, '');
    if (cpf.length !== 11 || /^(\d)\1{10}$/.test(cpf)) return false;

    let soma1 = 0;
    for (let i = 0; i < 9; i++) soma1 += parseInt(cpf.charAt(i)) * (10 - i);
    let digito1 = 11 - (soma1 % 11);
    if (digito1 >= 10) digito1 = 0;
    if (digito1 !== parseInt(cpf.charAt(9))) return false;

    let soma2 = 0;
    for (let i = 0; i < 10; i++) soma2 += parseInt(cpf.charAt(i)) * (11 - i);
    let digito2 = 11 - (soma2 % 11);
    if (digito2 >= 10) digito2 = 0;
    return digito2 === parseInt(cpf.charAt(10));
}

// Máscaras
document.addEventListener("DOMContentLoaded", function () {
    const cpfInput = document.getElementById("cpf");
    const telefoneInput = document.getElementById("telefone");

    if (cpfInput) {
        cpfInput.addEventListener("input", function () {
            let value = this.value.replace(/\D/g, '');
            if (value.length > 11) value = value.slice(0, 11);
            value = value.replace(/(\d{3})(\d)/, '$1.$2');
            value = value.replace(/(\d{3})(\d)/, '$1.$2');
            value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
            this.value = value;
        });
    }

    if (telefoneInput) {
        telefoneInput.addEventListener("input", function () {
            let value = this.value.replace(/\D/g, '');
            if (value.length > 11) value = value.slice(0, 11);
            if (value.length <= 10) {
                value = value.replace(/(\d{2})(\d{4})(\d{0,4})/, '($1) $2-$3');
            } else {
                value = value.replace(/(\d{2})(\d{5})(\d{0,4})/, '($1) $2-$3');
            }
            this.value = value;
        });
    }
});

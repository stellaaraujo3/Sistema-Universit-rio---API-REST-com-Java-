package org.exemplo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AlunosDAO alunoDAO = new AlunosDAO();
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final CursosDAO cursoDAO = new CursosDAO();
    private static final UsuarioView usuarioView = new UsuarioView();
    private static final TurmaDAO turmaDAO = new TurmaDAO();


    public static void main(String[] args) {

        usuarioDAO.createTable();
        alunoDAO.createTable();
        cursoDAO.createTable();
        turmaDAO.createTable();


        List<Cursos> cursosExistentes = cursoDAO.listarCursos();
        if (cursosExistentes.isEmpty()) {
            cursoDAO.inserirCurso(new Cursos(1, "Engenharia"));
            cursoDAO.inserirCurso(new Cursos(2, "Direito"));
            cursoDAO.inserirCurso(new Cursos(3, "Medicina"));
            cursoDAO.inserirCurso(new Cursos(4, "Arquitetura"));
            cursoDAO.inserirCurso(new Cursos(5, "Análise e Desenvolvimento de Sistemas"));
        }

        login();
    }

    private static void login() {
        System.out.println("PORTAL UNIVERSIT");
        System.out.print("Usuário: ");
        String username = scanner.nextLine().trim();
        System.out.print("Senha: ");
        String senha = scanner.nextLine().trim();

        Usuario usuario = usuarioDAO.login(username, senha);
        if (usuario == null || usuario.getusuario() == null || usuario.getsenha() == null) {
            System.out.println("Login inválido.");
            return;
        }

        if ("adm".equalsIgnoreCase(usuario.gettipousuario())) {
            menuAdm();
        } else {
            menuUsuario(usuario.getalunomatricula());
        }
    }

    private static void menuUsuario(int matricula) {
        System.out.println("Bem-vindo, aluno de matrícula " + matricula);
        Aluno a = alunoDAO.buscarPorMatricula(matricula);
        if (a != null) {
            System.out.println("Nome: " + a.getNome());
            System.out.println("Telefone: " + a.getTelefone());
            System.out.println("Data de Nascimento: " + a.getDataDeNascimento());
            System.out.println("Curso: " + a.getCurso());
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    private static void menuAdm() {
        while (true) {
            System.out.println("\n Menu do Administrador");
            System.out.println("1. Inserir novo aluno");
            System.out.println("2. Listar todos os alunos");
            System.out.println("3. Buscar aluno por matrícula");
            System.out.println("4. Buscar aluno por nome");
            System.out.println("5. Atualizar telefone de aluno");
            System.out.println("6. Remover aluno por matrícula");
            System.out.println("7. Gerenciar Usuários");
            System.out.println("8. Criar nova turma");
            System.out.println("9. Listar turmas");
            System.out.println("10. Remover turma por ID");
            System.out.println("11. Matricular aluno em turma");
            System.out.println("12. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> inserirAluno();
                case 2 -> listarTodos();
                case 3 -> buscarPorMatricula();
                case 4 -> buscarPorNome();
                case 5 -> atualizarTelefone();
                case 6 -> removerAluno();
                case 7 -> usuarioView.exibirMenu();
                case 8 -> criarTurma();
                case 9 -> listarTurmas();
                case 10 -> removerTurma();
                case 11 -> matricularAlunoEmTurma();
                case 12 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
    // MEDOTO PARA CRIAR TURMA
    private static void criarTurma() {
        System.out.print("Nome da turma: ");
        String nome = scanner.nextLine();
        System.out.print("Turno: ");
        String turno = scanner.nextLine();
        System.out.print("Curso: ");
        String curso = scanner.nextLine();

        turmaDAO.criarTurma(new Turma(nome, turno, curso));
    }
    // METODO PARA LISTAR TURMAS JA CADASTRADAS
    private static void listarTurmas() {
        List<Turma> turmas = turmaDAO.listarTurmas();
        for (Turma t : turmas) {
            System.out.println(t);
        }
    }
    // METODO PARA VINCULAR O ALUNA A UMA TURMA ATRAVES DA MATRICULA
    private static void matricularAlunoEmTurma() {
        System.out.print("Matrícula do aluno: ");
        int mat = scanner.nextInt();
        System.out.print("ID da turma: ");
        int turmaId = scanner.nextInt();
        turmaDAO.matricularAlunoEmTurma(mat, turmaId);
    }
    // METODO PARA CADASTRAR ALUNO NO SISTEMA
    private static void inserirAluno() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("Nome não pode ser vazio.");
                return;
            }

            System.out.print("Telefone (formato: (00)00000-0000): ");
            String telefone = scanner.nextLine().trim();
            if (!telefone.matches("\\(\\d{2}\\)\\d{4,5}-\\d{4}")) {
                System.out.println("Telefone inválido. Use o formato (XX)XXXXX-XXXX.");
                return;
            }

            LocalDate dataNascimento;
            String dataDigitada;
            do {
                System.out.print("Data de nascimento (DD/MM/YYYY): ");
                dataDigitada = scanner.nextLine().trim();

                try {
                    dataNascimento = LocalDate.parse(dataDigitada, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException e) {
                    System.out.println("Data inválida. Use o formato DD/MM/YYYY.");
                    dataNascimento = null;
                }
            } while (dataNascimento == null);

            List<Cursos> cursos = cursoDAO.listarCursos();
            System.out.println("Cursos disponíveis:");
            for (Cursos c : cursos) {
                System.out.println("- " + c.getNome());
            }

            String nomeCurso;
            int cursoId;
            do {
                System.out.print("Curso (digite exatamente como mostrado acima): ");
                nomeCurso = scanner.nextLine().trim();
                cursoId = cursoDAO.buscarIdPorNome(nomeCurso);
                if (cursoId == -1) {
                    System.out.println("Curso não encontrado! Tente novamente.");
                }
            } while (cursoId == -1);

            String cpf;
            do {
                System.out.print("CPF (formato: 000.000.000-00): ");
                cpf = scanner.nextLine().trim();

                if (!ValidadorCPF.isCPF(cpf)) {
                    System.out.println("CPF inválido. Por favor, digite um CPF válido.");
                }
            } while (!ValidadorCPF.isCPF(cpf));

            int matricula = gerarMatricula();
            Aluno novoAluno = new Aluno(matricula, nome, telefone, dataNascimento, nomeCurso, cpf);
            alunoDAO.inserirAluno(novoAluno);

            System.out.println("Aluno cadastrado com sucesso! Matrícula: " + matricula);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao cadastrar o aluno:");
            e.printStackTrace();
        }
    }
    // METODO PARA LISTAR TODOS OS ALUNOS MATRICULADOS
    private static void listarTodos() {
        List<Aluno> lista = alunoDAO.listarAlunos();
        for (Aluno a : lista) {
            System.out.println(a.getId() + " | " + a.getMatricula() + " | " +
                    a.getNome() + " | " + a.getTelefone() + " | " +
                    a.getDataDeNascimento() + " | " + a.getCurso() + " | " + a.getCpf());
        }
    }
    // METODO CRIADO PARA BUSCAR O ALUNO ATRAVES DA MATRICULA
    private static void buscarPorMatricula() {
        System.out.print("Digite a matrícula: ");
        int mat = scanner.nextInt();
        scanner.nextLine();
        Aluno aluno = alunoDAO.buscarPorMatricula(mat);
        if (aluno != null) {
            System.out.println(aluno.getNome() + " - " + aluno.getCurso());
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }
    // METODO PARA BUSCAR ALUNO PELO NOME
    private static void buscarPorNome() {
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        List<Aluno> lista = alunoDAO.buscarPorNome(nome);
        for (Aluno a : lista) {
            System.out.println(a.getMatricula() + " | " + a.getNome());
        }
    }
    // METODO PARA ATUALIZAR TELEFONE
    private static void atualizarTelefone() {
        System.out.print("Digite a matrícula do aluno: ");
        int mat = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite o novo telefone: ");
        String novoTelefone = scanner.nextLine();
        alunoDAO.atualizarTelefone(mat, novoTelefone);
    }
    //METODO PARA REMOVER ALUNO
    private static void removerAluno() {
        System.out.print("Digite a matrícula do aluno para remover: ");
        int mat = scanner.nextInt();
        alunoDAO.removerAluno(mat);
        System.out.println("Aluno removido com sucesso!");
    }
    //METODO PARA REMOVER TURMA
    private static void removerTurma() {
        System.out.print("Digite o ID da turma para remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        turmaDAO.removerTurmaPorId(id);
    }

    //METODO PARA GERAR MATRICULA AUTOMATICA
    public static int gerarMatricula() {
        LocalDate hoje = LocalDate.now();
        int numeroAleatorio = (int) (Math.random() * 1000);
        return Integer.parseInt(String.format("%04d%02d%03d", hoje.getYear(), hoje.getMonthValue(), numeroAleatorio));
    }
}
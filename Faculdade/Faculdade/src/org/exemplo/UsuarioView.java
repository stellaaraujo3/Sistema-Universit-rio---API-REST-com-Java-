package org.exemplo;

import org.exemplo.dao.UsuarioDAO;
import org.exemplo.model.Usuario;

import java.util.List;
import java.util.Scanner;


public class UsuarioView {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Scanner scanner = new Scanner(System.in);

    // METODO RESPONSAVEL POR EXIBIR O MENU DE OPÇOES AO USUARIO
    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 5) {
            System.out.println("\nGerenciamento de Usuários ");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Atualizar Usuário");
            System.out.println("4. Remover Usuário");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    atualizarUsuario();
                    break;
                case 4:
                    removerUsuario();
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
    // OS METODOS A BAIXO EXIBEM NO TERMINAL AS OPÇOES SELECIONADAS CHAMANDO OS METODOS DA CLASSE USUARIODAO
    private void cadastrarUsuario() {
        System.out.print("Usuário: ");
        String usuario = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Tipo (comum/admin): ");
        String tipo = scanner.nextLine();

        System.out.print("Aluno matrícula: ");
        String entrada = scanner.nextLine();
        Integer alunomatricula = null;

        if (!entrada.isBlank()) {
            try {
                alunomatricula = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Matrícula inválida. Deve ser um número inteiro.");
                return;
            }
        }


        Usuario u = new Usuario(usuario, senha, tipo, alunomatricula);
        usuarioDAO.adicionarUsuario(u);
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        System.out.println("\nLista de Usuários");
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    private void atualizarUsuario() {
        System.out.print("ID do usuário a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Novo Usuário (login): ");
        String usuario = scanner.nextLine();

        System.out.print("Nova Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Novo Tipo (comum/admin): ");
        String tipo = scanner.nextLine();

        System.out.print("Nova Matrícula do aluno (ou 0 se não tiver): ");
        int alunomatricula = scanner.nextInt();
        scanner.nextLine();

        Usuario u = new Usuario(id, usuario, senha, tipo, alunomatricula);
        usuarioDAO.atualizarUsuario(u);
    }

    private void removerUsuario() {
        System.out.print("ID do usuário a remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        usuarioDAO.removerUsuario(id);
        System.out.println("Usuario removido com sucesso!");
    }
}

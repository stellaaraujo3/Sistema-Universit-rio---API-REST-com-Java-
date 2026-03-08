package org.exemplo;

//METODO VALIDADOR DE CPF
public class ValidadorCPF {
    public static boolean isCPF(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}"))
            return false;

        try {
            int soma1 = 0, soma2 = 0;
            for (int i = 0; i < 9; i++) {
                int digito = cpf.charAt(i) - '0';
                soma1 += digito * (10 - i);
                soma2 += digito * (11 - i);
            }
            int dv1 = calcularDigito(soma1);
            soma2 += dv1 * 2;
            int dv2 = calcularDigito(soma2);
            return cpf.charAt(9) - '0' == dv1 && cpf.charAt(10) - '0' == dv2;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int calcularDigito(int soma) {
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}

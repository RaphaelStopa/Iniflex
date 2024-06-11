package org.example;

import org.example.domain.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


// Fiz em Java 11, devido a ser um dos mais utilizados e não ter sido especificado.
public class Main {

    public static final String NOME_BUSCA = "João";
    public static final String OPERADOR = "Operador";
    public static final String GERENTE = "Gerente";

    public static void main(String[] args) {

        // 3.1 - Inserir todos os funcionários
        List<Funcionario> funcionarios = criarFuncionarios();

        // 3.2 - Remover o funcionário "João"
        removerFuncionario(funcionarios);

        // 3.3 - Imprimir todos os funcionários
        imprimirFuncionarios(funcionarios);

        // 3.4 - Aumentar salário em 10%
        aumentarSalario(funcionarios);

        // 3.5 e 3.6 - Agrupar por função em um MAP
        imprimirFuncionariosPorFuncao(funcionarios);

        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        imprimirAniversariantes(funcionarios);

        // 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        imprimirFuncionarioMaisVelho(funcionarios);

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética.
        imprimirFuncionariosOrdenados(funcionarios);

        // 3.11 – Imprimir o total dos salários dos funcionários.
        imprimirTotalSalarios(funcionarios);

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        imprimirSalariosMinimos(funcionarios);

    }

    private static List<Funcionario> criarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), OPERADOR));
        funcionarios.add(new Funcionario(NOME_BUSCA, LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), OPERADOR));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Cordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), OPERADOR));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), GERENTE));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), GERENTE));
        return funcionarios;
    }

    private static void removerFuncionario(List<Funcionario> funcionarios) {
        Funcionario funcionarioRemover = funcionarios.stream()
                .filter(funcionario -> funcionario.getNome().equals(NOME_BUSCA))
                // Pois não foi definido outro critério de busca, podendo haver outro com o tempo.
                .findFirst()
                // Nunca cairá aqui porque estou usando enum, mas seria o correto.
                .orElse(null);
        if (!Objects.isNull(funcionarioRemover))
            funcionarios.remove(funcionarioRemover);
        else
            System.out.println(String.format("Funcionário %s não encontrado.", NOME_BUSCA));
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        System.out.println("\nFuncionários:");
        separadorTabelas();
        System.out.printf("%-20s %-15s %-15s %-20s\n", "Nome", "Data Nascimento", "Salário", "Função");
        separadorTabelas();
        funcionarios.forEach(Main::imprimirFuncionario);
        separadorTabelas();
    }

    private static void imprimirFuncionario(Funcionario funcionario) {
        String nome = funcionario.getNome();
        String dataNascimento = funcionario.getFormattedData();
        String salario = funcionario.getFormattedSalario();
        String funcao = funcionario.getFuncao();
        System.out.printf("%-20s %-15s %-15s %-20s\n", nome, dataNascimento, salario, funcao);
    }

    private static void aumentarSalario(List<Funcionario> funcionarios) {
        funcionarios.forEach(funcionario -> funcionario.setSalario(funcionario.getSalario().multiply(new BigDecimal("1.1"))));
    }

    private static void imprimirFuncionariosPorFuncao(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        separadorTabelas();
        System.out.println("\nFuncionários por função:");
        separadorTabelas();
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.printf("Função: %s\n", funcao);
            lista.forEach(funcionario -> System.out.println(funcionario.getNome()));
            separadorTabelas();
        });
    }
    private static void imprimirAniversariantes(List<Funcionario> funcionarios) {
        separadorTabelas();
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10 || funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(funcionario -> System.out.println(funcionario.getNome() + ", " + funcionario.getFormattedData()));
        separadorTabelas();
    }

    private static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        separadorTabelas();
        System.out.println("\nFuncionário com maior idade:");
        Funcionario maisVelho = funcionarios.stream()
                .min((f1, f2) -> f1.getDataNascimento().compareTo(f2.getDataNascimento()))
                .orElse(null);
        if (maisVelho != null) {
            long idade = java.time.temporal.ChronoUnit.YEARS.between(maisVelho.getDataNascimento(), LocalDate.now());
            System.out.println("Nome: " + maisVelho.getNome() + ", Idade: " + idade);
        }
        separadorTabelas();
    }

    private static void imprimirFuncionariosOrdenados(List<Funcionario> funcionarios) {
        separadorTabelas();
        System.out.println("\nLista de funcionários em ordem alfabética:");
        funcionarios.stream()
                .sorted((f1, f2) -> f1.getNome().compareTo(f2.getNome()))
                .forEach(funcionario -> System.out.println(funcionario.getNome()));
        separadorTabelas();
    }

    private static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        separadorTabelas();
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários dos funcionários: " + totalSalarios.toString().replace('.', ','));
        separadorTabelas();
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios) {
        separadorTabelas();
        System.out.println("\nSalários mínimos dos funcionários:");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_DOWN);
            System.out.println(funcionario.getNome() + ": " + salariosMinimos);
        });
        separadorTabelas();
    }

    private static void separadorTabelas(){
        System.out.println("-------------------------------------------------------------------------");
    }
}
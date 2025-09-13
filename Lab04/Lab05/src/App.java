import java.util.List;
import java.math.BigDecimal;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ContaDao dao;
        Scanner scanner = new Scanner(System.in);
        String url = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?user=postgres.bvdonywsxjzmlhxdcixv&password=87iwpcTT355oTDt3";

        try {
            dao = new ContaDao(ConnectionFactory.getConnection(url));
            int opcao = -1;

            while (opcao != 0) {
                System.out.println("\n--- Menu ---");
                System.out.println("(1) Listar todas as contas");
                System.out.println("(2) Buscar uma conta específica pelo número");
                System.out.println("(3) Criar uma nova conta");
                System.out.println("(4) Alterar o saldo de uma conta");
                System.out.println("(5) Apagar uma conta");
                System.out.println("(0) Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consome a quebra de linha

                switch (opcao) {
                    case 1:
                        List<Conta> contas = dao.lerTodas();
                        if (contas.isEmpty()) {
                            System.out.println("Nenhuma conta encontrada.");
                        } else {
                            contas.forEach(System.out::println);
                        }
                        break;
                    case 2:
                        System.out.print("Digite o número da conta para buscar: ");
                        long numeroBusca = scanner.nextLong();
                        Conta contaBuscada = dao.buscarPeloNumero(numeroBusca);
                        if (contaBuscada != null) {
                            System.out.println("Conta encontrada: " + contaBuscada);
                        } else {
                            System.out.println("Conta não encontrada.");
                        }
                        break;
                    case 3:
                        System.out.print("Digite o número da nova conta: ");
                        long novoNumero = scanner.nextLong();
                        System.out.print("Digite o saldo inicial: ");
                        BigDecimal novoSaldo = scanner.nextBigDecimal();
                        Conta novaConta = new Conta(novoNumero, novoSaldo);
                        if (dao.criar(novaConta)) {
                            System.out.println("Conta criada com sucesso.");
                        } else {
                            System.out.println("Falha ao criar a conta.");
                        }
                        break;
                    case 4:
                        System.out.print("Digite o número da conta para alterar: ");
                        long numeroAtualiza = scanner.nextLong();
                        Conta contaAtualiza = dao.buscarPeloNumero(numeroAtualiza);
                        if (contaAtualiza != null) {
                            System.out.print("Digite o novo saldo: ");
                            BigDecimal novoSaldoAtualiza = scanner.nextBigDecimal();
                            contaAtualiza.setSaldo(novoSaldoAtualiza);
                            if (dao.atualizar(contaAtualiza)) {
                                System.out.println("Conta atualizada com sucesso.");
                            } else {
                                System.out.println("Falha ao atualizar a conta.");
                            }
                        } else {
                            System.out.println("Conta não encontrada.");
                        }
                        break;
                    case 5:
                        System.out.print("Digite o número da conta para apagar: ");
                        long numeroApagar = scanner.nextLong();
                        Conta contaApagar = new Conta(numeroApagar, BigDecimal.ZERO);
                        if (dao.apagar(contaApagar)) {
                            System.out.println("Conta apagada com sucesso.");
                        } else {
                            System.out.println("Falha ao apagar a conta.");
                        }
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao conectar ou operar no banco de dados.");
        } finally {
            scanner.close();
        }
    }
}
package com.poo.locadora.util;

import com.poo.locadora.entities.Cliente;
import com.poo.locadora.entities.Locacao;
import com.poo.locadora.entities.Veiculo;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);

    public static void MenuLocacao() {
        while (true) {
            System.out.println("----------------------------");
            System.out.println("Escolha uma opção:");
            System.out.println("1: Cadastrar um cliente");
            System.out.println("2: Alugar um veículo");
            System.out.println("3: Devolver um veículo");
            System.out.println("4: Verificar veículos disponíveis");
            System.out.println("5: Verificar veículos alugados por cliente");
            System.out.println("6: Sair do sistema");

            int opcao = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    alugarVeiculo();
                    break;
                case 3:
                    devolverVeiculo();
                    break;
                case 4:
                    verificarVeiculosDisponiveis();
                    break;
                case 5:
                    verificarVeiculosPorCliente();
                    break;
                case 6:
                    System.out.println("Saindo do sistema...");
                    return;  // Encerra o programa
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.nextLine();
        System.out.println("Digite a data de nascimento (yyyy-MM-dd):");
        LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());

        // Verificar se o cliente já existe pelo CPF
        if (buscarClientePorCpf(cpf) != null) {
            System.out.println("Cliente já cadastrado.");
            return;
        }

        // Verificar se o cliente tem pelo menos 18 anos
        if (Period.between(dataNascimento, LocalDate.now()).getYears() < 18) {
            System.out.println("Cliente precisa ter pelo menos 18 anos.");
            return;
        }

        Cliente novoCliente = new Cliente(nome, cpf, dataNascimento);
        salvarCliente(novoCliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void alugarVeiculo() {
        System.out.println("Informe o CPF do cliente:");
        String cpfCliente = scanner.nextLine();
        Cliente cliente = buscarClientePorCpf(cpfCliente);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        // Verificar se o cliente tem pelo menos 18 anos (caso não tenha sido feito na hora do cadastro)
        if (Period.between(cliente.getDataNascimento(), LocalDate.now()).getYears() < 18) {
            System.out.println("Cliente deve ter pelo menos 18 anos para alugar um veículo.");
            return;
        }

        System.out.println("Informe a placa do veículo para locação:");
        String placaVeiculo = scanner.nextLine();
        Veiculo veiculo = buscarVeiculoPorPlaca(placaVeiculo);

        if (veiculo == null || veiculo.getQuantidade() <= 0) {
            System.out.println("Veículo não encontrado ou indisponível.");
            return;
        }

        // Solicitar a data de locação
        System.out.println("Informe a data de locação (yyyy-MM-dd) ou pressione Enter para usar a data atual:");
        String dataLocacaoInput = scanner.nextLine();
        LocalDate dataLocacao = dataLocacaoInput.isEmpty() ? LocalDate.now() : LocalDate.parse(dataLocacaoInput);

        // Criar e salvar locação com diaDevolucao como null
        Locacao locacao = new Locacao();
        locacao.setDiaLocacao(dataLocacao);
        locacao.setDiaDevolucao(null);  // Devolução ainda não definida
        locacao.setCliente(cliente);
        locacao.setVeiculo(veiculo);

        salvarLocacao(locacao);

        // Atualizar quantidade de veículos
        veiculo.setQuantidade(veiculo.getQuantidade() - 1);
        atualizarVeiculo(veiculo);

        System.out.println("Veículo alugado com sucesso!");
    }

    private static void devolverVeiculo() {
        System.out.println("Informe o CPF do cliente:");
        String cpfCliente = scanner.nextLine();
        Cliente cliente = buscarClientePorCpf(cpfCliente);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        // Verificar se o cliente tem algum veículo alugado
        List<Locacao> locacoesAtivas = buscarLocacoesAtivas(cliente);
        if (locacoesAtivas.isEmpty()) {
            System.out.println("Este cliente não tem veículos alugados.");
            return;
        }

        System.out.println("Informe a placa do veículo para devolução:");
        String placaVeiculo = scanner.nextLine();
        Veiculo veiculo = buscarVeiculoPorPlaca(placaVeiculo);

        if (veiculo == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        Locacao locacao = buscarLocacaoPorVeiculo(placaVeiculo, cliente);
        if (locacao == null) {
            System.out.println("Este veículo não foi alugado por este cliente.");
            return;
        }

        locacao.setDiaDevolucao(LocalDate.now());
        atualizarLocacao(locacao);

        // Atualizar a quantidade de veículos
        veiculo.setQuantidade(veiculo.getQuantidade() + 1);
        atualizarVeiculo(veiculo);

        System.out.println("Veículo devolvido com sucesso!");
    }

    private static void verificarVeiculosDisponiveis() {
        System.out.println("Lista de veículos disponíveis:");

        // Consultar os veículos disponíveis
        List<Veiculo> veiculosDisponiveis = listarVeiculosDisponiveis();

        // Verificar se a lista não está vazia
        if (veiculosDisponiveis.isEmpty()) {
            System.out.println("Nenhum veículo disponível no momento.");
        } else {
            // Exibir os veículos disponíveis
            for (Veiculo veiculo : veiculosDisponiveis) {
                System.out.println("Placa: " + veiculo.getPlaca() +
                        ", Marca: " + veiculo.getMarca() +
                        ", Modelo: " + veiculo.getModelo() +
                        ", Cor: " + veiculo.getCor() +
                        ", Ano: " + veiculo.getAnoFabricacao() +
                        ", Quantidade disponível: " + veiculo.getQuantidade());
            }
        }
    }

    private static void verificarVeiculosPorCliente() {
        System.out.println("Informe o CPF do cliente para verificar seus veículos alugados:");
        String cpfCliente = scanner.nextLine();
        Cliente cliente = buscarClientePorCpf(cpfCliente);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        List<Locacao> locacoesAtivas = buscarLocacoesAtivas(cliente);
        if (locacoesAtivas.isEmpty()) {
            System.out.println("Este cliente não tem veículos alugados.");
        } else {
            for (Locacao locacao : locacoesAtivas) {
                System.out.println("Veículo: " + locacao.getVeiculo().getModelo() +
                        ", Placa: " + locacao.getVeiculo().getPlaca() +
                        ", Data de locação: " + locacao.getDiaLocacao());
            }
        }
    }

    // Métodos de consulta e manipulação no banco de dados

    private static Cliente buscarClientePorCpf(String cpf) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Cliente WHERE cpf = :cpf", Cliente.class)
                    .setParameter("cpf", cpf)
                    .uniqueResult();
        } finally {
            session.close();
        }
    }

    private static Veiculo buscarVeiculoPorPlaca(String placa) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Veiculo WHERE placa = :placa", Veiculo.class)
                    .setParameter("placa", placa)
                    .uniqueResult();
        } finally {
            session.close();
        }
    }

    private static List<Veiculo> listarVeiculosDisponiveis() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Veiculo WHERE quantidade > 0", Veiculo.class).list();
        } finally {
            session.close();
        }
    }

    private static List<Locacao> buscarLocacoesAtivas(Cliente cliente) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Locacao WHERE cliente = :cliente AND diaDevolucao IS NULL", Locacao.class)
                    .setParameter("cliente", cliente)
                    .list();
        } finally {
            session.close();
        }
    }

    private static Locacao buscarLocacaoPorVeiculo(String placa, Cliente cliente) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Locacao WHERE veiculo.placa = :placa AND cliente = :cliente", Locacao.class)
                    .setParameter("placa", placa)
                    .setParameter("cliente", cliente)
                    .uniqueResult();
        } finally {
            session.close();
        }
    }

    private static void salvarCliente(Cliente cliente) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(cliente);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void salvarLocacao(Locacao locacao) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(locacao);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void atualizarVeiculo(Veiculo veiculo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(veiculo);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void atualizarLocacao(Locacao locacao) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(locacao);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

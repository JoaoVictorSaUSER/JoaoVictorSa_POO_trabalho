import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class ItemVenda {
    private String nome;
    private double preco;

    public ItemVenda(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}

class Pizza extends ItemVenda {
    private String recheio;
    private boolean bordaRecheada;
    private String molho;

    public Pizza(String nome, double preco) {
        super(nome, preco);
    }

    public void setRecheio(String recheio) {
        this.recheio = recheio;
    }

    public void setBordaRecheada(boolean bordaRecheada) {
        this.bordaRecheada = bordaRecheada;
    }

    public void setMolho(String molho) {
        this.molho = molho;
    }
}

class Lanche extends ItemVenda {
    private String tipoPao;
    private String recheio;
    private String molho;

    public Lanche(String nome, double preco) {
        super(nome, preco);
    }

    public void setTipoPao(String tipoPao) {
        this.tipoPao = tipoPao;
    }

    public void setRecheio(String recheio) {
        this.recheio = recheio;
    }

    public void setMolho(String molho) {
        this.molho = molho;
    }
}

class Salgadinho extends ItemVenda {
    private String tipo;
    private String massa;
    private String recheio;

    public Salgadinho(String nome, double preco) {
        super(nome, preco);
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMassa(String massa) {
        this.massa = massa;
    }

    public void setRecheio(String recheio) {
        this.recheio = recheio;
    }
}

class Pedido {
    private String nomeCliente;
    private List<ItemVenda> itens;
    private double taxaServico;

    public Pedido(String nomeCliente) {
        this.nomeCliente = nomeCliente;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemVenda item) {
        itens.add(item);
    }

    public void setTaxaServico(double taxaServico) {
        this.taxaServico = taxaServico;
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemVenda item : itens) {
            total += item.getPreco();
        }
        total += taxaServico;
        return total;
    }

    public void imprimirNotaFiscal() {
        System.out.println("========== Nota Fiscal ==========");
        System.out.println("Cliente: " + nomeCliente);
        System.out.println("Itens:");

        for (ItemVenda item : itens) {
            System.out.println("- " + item.getNome() + ": R$" + item.getPreco());
        }

        System.out.println("Taxa de Serviço: R$" + taxaServico);
        System.out.println("Total: R$" + calcularTotal());
        System.out.println("=================================");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Pedido pedido = criarPedido(scanner);

        pedido.imprimirNotaFiscal();

        System.out.println("Pedido finalizado. Obrigado!");
        scanner.close();
    }

    private static Pedido criarPedido(Scanner scanner) {
        System.out.print("Informe o nome do cliente: ");
        String nomeCliente = scanner.nextLine();

        Pedido pedido = new Pedido(nomeCliente);

        boolean continuarPedido = true;
        while (continuarPedido) {
            System.out.println("Escolha o tipo de item:");
            System.out.println("1 - Pizza");
            System.out.println("2 - Lanche");
            System.out.println("3 - Salgadinho");
            System.out.println("0 - Finalizar pedido");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do teclado

            switch (opcao) {
                case 1:
                    adicionarPizza(scanner, pedido);
                    break;
                case 2:
                    adicionarLanche(scanner, pedido);
                    break;
                case 3:
                    adicionarSalgadinho(scanner, pedido);
                    break;
                case 0:
                    continuarPedido = false;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha novamente.");
            }
        }

        System.out.print("Informe a taxa de serviço: ");
        double taxaServico = scanner.nextDouble();
        pedido.setTaxaServico(taxaServico);

        return pedido;
    }

    private static void adicionarPizza(Scanner scanner, Pedido pedido) {
        System.out.println("Escolha o sabor da pizza:");
        Map<Integer, Pizza> opcoesPizza = new HashMap<>();
        opcoesPizza.put(1, criarPizza("Mussarela", 30.0));
        opcoesPizza.put(2, criarPizza("Calabresa", 35.0));
        opcoesPizza.put(3, criarPizza("Portuguesa", 35.0));

        for (Map.Entry<Integer, Pizza> entry : opcoesPizza.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getNome() + ": R$" + entry.getValue().getPreco());
        }

        System.out.print("Escolha o número correspondente ao sabor desejado: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do teclado

        Pizza pizzaEscolhida = opcoesPizza.get(escolha);

        System.out.print("Deseja borda recheada? (S/N): ");
        String escolhaBorda = scanner.nextLine();
        if (escolhaBorda.equalsIgnoreCase("S")) {
            pizzaEscolhida.setBordaRecheada(true);
        } else {
            pizzaEscolhida.setBordaRecheada(false);
        }

        System.out.print("Informe o molho da pizza: ");
        String molho = scanner.nextLine();
        pizzaEscolhida.setMolho(molho);

        pedido.adicionarItem(pizzaEscolhida);
    }

    private static Pizza criarPizza(String nome, double preco) {
        Pizza pizza = new Pizza(nome, preco);
        return pizza;
    }

    private static void adicionarLanche(Scanner scanner, Pedido pedido) {
        System.out.println("Escolha o tipo de lanche:");
        Map<Integer, Lanche> opcoesLanche = new HashMap<>();
        opcoesLanche.put(1, criarLanche("X-Burger", 15.0));
        opcoesLanche.put(2, criarLanche("X-Salada", 18.0));
        opcoesLanche.put(3, criarLanche("X-Tudo", 20.0));

        for (Map.Entry<Integer, Lanche> entry : opcoesLanche.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getNome() + ": R$" + entry.getValue().getPreco());
        }

        System.out.print("Escolha o número correspondente ao lanche desejado: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do teclado

        Lanche lancheEscolhido = opcoesLanche.get(escolha);

        System.out.print("Informe o tipo de pão: ");
        String tipoPao = scanner.nextLine();
        lancheEscolhido.setTipoPao(tipoPao);

        System.out.print("Informe o recheio do lanche: ");
        String recheio = scanner.nextLine();
        lancheEscolhido.setRecheio(recheio);

        System.out.print("Informe o molho do lanche: ");
        String molho = scanner.nextLine();
        lancheEscolhido.setMolho(molho);

        pedido.adicionarItem(lancheEscolhido);
    }

    private static Lanche criarLanche(String nome, double preco) {
        Lanche lanche = new Lanche(nome, preco);
        return lanche;
    }

    private static void adicionarSalgadinho(Scanner scanner, Pedido pedido) {
        System.out.println("Escolha o tipo de salgadinho:");
        Map<Integer, Salgadinho> opcoesSalgadinho = new HashMap<>();
        opcoesSalgadinho.put(1, criarSalgadinho("Coxinha", 2.5));
        opcoesSalgadinho.put(2, criarSalgadinho("Pastel", 3.0));
        opcoesSalgadinho.put(3, criarSalgadinho("Enroladinho de Salsicha", 2.0));

        for (Map.Entry<Integer, Salgadinho> entry : opcoesSalgadinho.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getNome() + ": R$" + entry.getValue().getPreco());
        }

        System.out.print("Escolha o número correspondente ao salgadinho desejado: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do teclado

        Salgadinho salgadinhoEscolhido = opcoesSalgadinho.get(escolha);

        System.out.print("Informe o tipo (frito ou assado): ");
        String tipo = scanner.nextLine();
        salgadinhoEscolhido.setTipo(tipo);

        System.out.print("Informe o recheio do salgadinho: ");
        String recheio = scanner.nextLine();
        salgadinhoEscolhido.setRecheio(recheio);

        pedido.adicionarItem(salgadinhoEscolhido);
    }

    private static Salgadinho criarSalgadinho(String nome, double preco) {
        Salgadinho salgadinho = new Salgadinho(nome, preco);
        return salgadinho;
    }
}

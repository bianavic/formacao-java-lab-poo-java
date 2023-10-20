package one.digitalinovation.laboojava.console;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.Caderno;
import one.digitalinovation.laboojava.entidade.Cliente;
import one.digitalinovation.laboojava.entidade.Cupom;
import one.digitalinovation.laboojava.entidade.Livro;
import one.digitalinovation.laboojava.entidade.Pedido;
import one.digitalinovation.laboojava.negocio.ClienteNegocio;
import one.digitalinovation.laboojava.negocio.PedidoNegocio;
import one.digitalinovation.laboojava.negocio.ProdutoNegocio;
import one.digitalinovation.laboojava.utilidade.LeitoraDados;

import java.util.Optional;

/**
 * Classe responsável por controlar a execução da aplicação.
 */
public class Start {

    private static Cliente clienteLogado = null;

    private static Banco banco = new Banco();

    private static ClienteNegocio clienteNegocio = new ClienteNegocio(banco);
    private static PedidoNegocio pedidoNegocio = new PedidoNegocio(banco);
    private static ProdutoNegocio produtoNegocio = new ProdutoNegocio(banco);

    /**
     * Método utilitário para inicializar a aplicação.
     *
     * @param args Parâmetros que podem ser passados para auxiliar na execução.
     */
    public static void main(String[] args) {

        System.out.println("Bem vindo ao e-Compras");

        String opcao = "";

        while (true) {

            if (clienteLogado == null) {

                System.out.println("Digite o cpf:");

                String cpf = "";
                cpf = LeitoraDados.lerDado();

                identificarUsuario(cpf);
            }

            System.out.println("Selecione uma opção:");

            System.out.println("1 - Cadastrar Livro");
            System.out.println("2 - Excluir Livro");
            System.out.println("3 - Consultar Livro pelo Nome");
            System.out.println("----------------------");
            System.out.println("4 - Cadastrar Caderno");
            System.out.println("5 - Excluir Caderno");
            System.out.println("6 - Consultar Caderno pelo Tipo");
            System.out.println("----------------------");
            System.out.println("7 - Fazer pedido");
            System.out.println("8 - Excluir pedido");
            System.out.println("9 - Consultar Pedido pelo Código");
            System.out.println("----------------------");
            System.out.println("10 - Listar produtos");
            System.out.println("11 - Listar pedidos");
            System.out.println("----------------------");
            System.out.println("12 - Cadastrar Cliente");
            System.out.println("13 - Excluir Clientes");
            System.out.println("14 - Consultar Cliente");
            System.out.println("15 - Listar Clientes");
            System.out.println("----------------------");
            System.out.println("16 - Deslogar");
            System.out.println("17 - Sair");

            opcao = LeitoraDados.lerDado();

            switch (opcao) {
                case "1":
                    Livro livro = LeitoraDados.lerLivro();
                    produtoNegocio.salvarProduto(livro);
                    break;
                case "2":
                    System.out.println("Digite o código do livro");
                    String codigoLivro = LeitoraDados.lerDado();
                    produtoNegocio.excluirProduto(codigoLivro);
                    break;
                case "3":
                    System.out.println("Digite o nome do livro");
                    String nome = LeitoraDados.lerDado();
                    produtoNegocio.consultarLivroPeloNome(nome);
                    break;
                case "4":
                    Caderno caderno = LeitoraDados.lerCaderno();
                    produtoNegocio.salvarProduto(caderno);
                    break;
                case "5":
                    System.out.println("Digite o código do caderno");
                    String codigoCaderno = LeitoraDados.lerDado();
                    produtoNegocio.excluirProduto(codigoCaderno);
                    break;
                case "6":
                    System.out.println("Digite o tipo de materia do caderno");
                    String tipoCaderno = LeitoraDados.lerDado();
                    produtoNegocio.consultarCadernoPeloTipo(tipoCaderno);
                    break;
                case "7":
                    Pedido pedido = LeitoraDados.lerPedido(banco);
                    Optional<Cupom> cupom = LeitoraDados.lerCupom(banco);

                    if (cupom.isPresent()) {
                        pedidoNegocio.salvarPedido(pedido, cupom.get());
                    } else {
                        pedidoNegocio.salvarPedido(pedido);
                    }
                    break;
                case "8":
                    System.out.println("Digite o código do pedido");
                    String codigoPedido = LeitoraDados.lerDado();
                    pedidoNegocio.excluirPedido(codigoPedido);
                    break;
                case "9":
                    System.out.println("Digite o código do pedido");
                    String codigo = LeitoraDados.lerDado();
                    Optional<Pedido> pedidoOptional = pedidoNegocio.consultarPedido(codigo);
                    if (pedidoOptional.isPresent()) {
                        Pedido pedidoConsultado = pedidoOptional.get();
                        System.out.println("Pedido encontrado:");
                        System.out.println(pedidoConsultado.toString());
                    } else {
                        System.out.println("Pedido não encontrado.");
                    }
                    break;
                case "10":
                    produtoNegocio.listarTodosProdutos();
                    break;
                case "11":
                    pedidoNegocio.listarTodosPedidos();
                    break;
                case "12":
                    Cliente novoCliente = LeitoraDados.lerCliente();
                    clienteNegocio.cadastrarNovoCliente(novoCliente);
                    clienteLogado = novoCliente;
                    break;
                case "13":
                    System.out.println("Digite o cpf:");
                    String cpfCliente = "";
                    cpfCliente = LeitoraDados.lerDado();
                    excluirCliente(cpfCliente);
                    break;
                case "14":
                    System.out.println("Digite o cpf:");
                    String cpf = "";
                    cpf = LeitoraDados.lerDado();
                    consultarCliente(cpf);
                    break;
                case "15":
                    clienteNegocio.listarClientes();
                    break;
                case "16":
                    System.out.println(String.format("Volte sempre %s!", clienteLogado.getNome()));
                    clienteLogado = null;
                    break;
                case "17":
                    System.out.println("Aplicação encerrada.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    /**
     * Procura o usuário na base de dados.
     *
     * @param cpf CPF do usuário que deseja logar na aplicação
     */
    private static void identificarUsuario(String cpf) {

        Optional<Cliente> resultado = clienteNegocio.consultar(cpf);

        if (resultado.isPresent()) {

            Cliente cliente = resultado.get();
            System.out.println(String.format("Olá %s! Você está logado.", cliente.getNome()));
            clienteLogado = cliente;
        } else {
            System.out.println("Usuário não cadastrado.");
            System.exit(0);
        }
    }

    private static void excluirCliente(String cpf) {
        Cliente cliente = buscaCliente(cpf);
        if (cliente.getCpf().equals(""))
            System.out.println("Cliente não encontrado");
        else clienteNegocio.excluir(cpf);
    }

    private static void consultarCliente(String cpf) {
        Cliente cliente = buscaCliente(cpf);
        if (cliente.getCpf().equals(""))
            System.out.println(String.format("Cliente não encontrado"));
        else
            System.out.println(String.format("Cliente encontrado: Nome - " + cliente.getNome() + " Cpf: " + cliente.getCpf()));
    }

    private static Cliente buscaCliente(String cpf) {
        Optional<Cliente> resultado = clienteNegocio.consultar(cpf);
        Cliente cliente = resultado.get();
        return cliente;
    }

}

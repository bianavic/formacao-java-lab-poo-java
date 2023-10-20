package one.digitalinovation.laboojava.negocio;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.Cupom;
import one.digitalinovation.laboojava.entidade.Pedido;
import one.digitalinovation.laboojava.entidade.Produto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Classe para manipular a entidade {@link Pedido}.
 */
public class PedidoNegocio {

    /**
     * {@inheritDoc}.
     */
    private Banco bancoDados;

    /**
     * Construtor.
     *
     * @param banco Banco de dados para ter armazenar e ter acesso os pedidos
     */
    public PedidoNegocio(Banco banco) {
        this.bancoDados = banco;
    }

    private double calcularTotalPedido(List<Produto> produtos, Cupom cupom) {

        double total = 0.0;
        for (Produto produto : produtos) {
            total += produto.calcularFrete();
        }

        if (cupom != null) {
            return total * (1 - cupom.getDesconto());
        } else {
            return total;
        }

    }

    /**
     * Salva um novo pedido sem cupom de desconto.
     *
     * @param novoPedido Pedido a ser armazenado
     */
    public void salvarPedido(Pedido novoPedido) {
        salvarPedido(novoPedido, null);
    }

    /**
     * Salva um novo pedido com cupom de desconto.
     *
     * @param novoPedido Pedido a ser armazenado
     * @param cupom      Cupom de desconto a ser utilizado
     */
    // SOBRECARGA: sobrecarreguei metodo salvar (alterada lista de parametros)
    // para salvar o pedido com cupom
    public void salvarPedido(Pedido novoPedido, Cupom cupom) {

        String codigo = "PE%4d%2d%04d";
        LocalDate hoje = LocalDate.now();
        codigo = String.format(codigo, hoje.getYear(), hoje.getMonthValue(), bancoDados.getPedidos().length);

        novoPedido.setCodigo(codigo);
        novoPedido.setCliente(bancoDados.getCliente());
        novoPedido.setTotal(calcularTotalPedido(novoPedido.getProdutos(), cupom));
        //Adicionar no banco
        bancoDados.adicionarPedido(novoPedido);
        System.out.println("Pedido salvo com sucesso");
    }

    /**
     * Exclui um pedido a partir de seu código de rastreio.
     *
     * @param codigo Código do pedido
     */
    public void excluirPedido(String codigo) {

        int pedidoExclusao = -1;
        for (int i = 0; i < bancoDados.getPedidos().length; i++) {

            Pedido pedido = bancoDados.getPedidos()[i];
            if (pedido.getCodigo().equals(codigo)) {
                pedidoExclusao = i;
                break;
            }
        }

        if (pedidoExclusao != -1) {
            bancoDados.removerPedido(pedidoExclusao);
            System.out.println("Pedido excluído com sucesso.");
        } else {
            System.out.println("Pedido inexistente.");
        }
    }

    /**
     * Lista todos os pedidos realizados.
     */
    public void listarTodosPedidos() {

        if (bancoDados.getPedidos().length == 0) {
            System.out.println("Não existem pedidos cadastrados");
        } else {

            for (Pedido pedido : bancoDados.getPedidos()) {
                System.out.println(pedido.toString());
            }

        }
    }

    public Optional<Pedido> consultarPedido(String codigo) {

        for (Pedido pedido : bancoDados.getPedidos()) {

            if (pedido.getCodigo().equalsIgnoreCase(codigo)) {
                return Optional.of(pedido);
            }
        }
        return Optional.empty();
    }

}

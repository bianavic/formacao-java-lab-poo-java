package one.digitalinovation.laboojava.entidade;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa a entidade pedido, qual é a compra dos produtos por um cliente.
 */
public class Pedido {

    private String codigo;
    private Cliente cliente; // associacao
    private List<Produto> produtos;
    private double total;

    // para evitar null pointer, instanciar, inicializar produtos
    // OBS: com hibernate nao funcionaria
    public Pedido() {
        this.produtos = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    // listar os produtos no toString abaixo
    private String getProdutosComprados() {
        StringBuilder produtos = new StringBuilder();
        produtos.append("[");
        for (Produto produto : getProdutos()) {
            produtos.append(produtos.toString());
            produtos.append(" Qtdd: ");
            produtos.append(produto.getQuantidade());
            produtos.append(" ");
        }
        produtos.append("]");

        return produtos.toString();
    }

    // toString pq iremos exibir os pedidos
    @Override
    public String toString() {
        return "Pedido{" +
                "codigo='" + codigo + '\'' +
                ", cliente=" + cliente +
                ", produtos=" + getProdutosComprados() +
                ", total=" + total +
                '}';
    }

}

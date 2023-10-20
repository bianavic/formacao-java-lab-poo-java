package one.digitalinovation.laboojava.negocio;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.Caderno;
import one.digitalinovation.laboojava.entidade.Livro;
import one.digitalinovation.laboojava.entidade.Produto;

import java.util.Optional;

/**
 * Classe para manipular a entidade {@link Produto}.
 */
public class ProdutoNegocio {

    /**
     * {@inheritDoc}.
     */
    private Banco bancoDados;

    /**
     * Construtor.
     *
     * @param banco Banco de dados para ter armazenar e ter acesso os produtos
     */
    public ProdutoNegocio(Banco banco) {
        this.bancoDados = banco;
    }

    /**
     * Salva um novo produto(livro ou caderno) na loja.
     *
     * @param novoProduto Livro ou caderno que pode ser vendido
     */
    public void salvarProduto(Produto novoProduto) {

        String codigo = "PR%04d";
        codigo = String.format(codigo, bancoDados.getProdutos().length);
        novoProduto.setCodigo(codigo);

        boolean produtoRepetido = false;
        for (Produto produto : bancoDados.getProdutos()) {
            if (produto.getCodigo().equalsIgnoreCase(novoProduto.getCodigo())) {
                produtoRepetido = true;
                System.out.println("Produto já cadastrado.");
                break;
            }
        }

        if (!produtoRepetido) {
            this.bancoDados.adicionarProduto(novoProduto);
            System.out.println("Produto cadastrado com sucesso.");
        }
    }

    /**
     * Exclui um produto pelo código de cadastro.
     *
     * @param codigo Código de cadastro do produto
     */
    public void excluirProduto(String codigo) {

        int produtoExclusao = -1;
        for (int i = 0; i < bancoDados.getProdutos().length; i++) {

            Produto produto = bancoDados.getProdutos()[i];
            if (produto.getCodigo().equals(codigo)) {
                produtoExclusao = i;
                break;
            }
        }

        if (produtoExclusao != -1) {
            bancoDados.removerProduto(produtoExclusao);
            System.out.println("Produto excluído com sucesso.");
        } else {
            System.out.println("Produto inexistente.");
        }
    }

    /**
     * Obtem um produto a partir de seu código de cadastro.
     *
     * @param codigo Código de cadastro do produto
     * @return Optional indicando a existência ou não do Produto
     */
    public Optional<Produto> consultarProduto(String codigo) {

        for (Produto produto : bancoDados.getProdutos()) {

            if (produto.getCodigo().equalsIgnoreCase(codigo)) {
                return Optional.of(produto);
            }
        }

        return Optional.empty();
    }

    public void consultarLivroPeloNome(String nome) {

        for (Produto produto : bancoDados.getProdutos()) {

            if (produto instanceof Livro && ((Livro) produto).getNome().equalsIgnoreCase(nome)) {
                System.out.println(produto.toString());
                return;
            }

        }
        System.out.println("Não existe livro cadastrado com esse nome");
    }

    public void consultarCadernoPeloTipo(String tipo) {


        for (Produto produto : bancoDados.getProdutos()) {

            if (produto instanceof Caderno) {
                Caderno caderno = (Caderno) produto;
                if (caderno.getTipo().toString().equalsIgnoreCase(tipo)) {
                    System.out.println(caderno.toString());
                    return;
                }
            }

        }
        System.out.println("Não existe caderno cadastrado com esse tipo");
    }

    /**
     * Lista todos os produtos cadastrados.
     */
    public void listarTodosProdutos() {

        if (bancoDados.getProdutos().length == 0) {
            System.out.println("Não existem produtos cadastrados");
        } else {

            for (Produto produto : bancoDados.getProdutos()) {
                System.out.println(produto.toString());
            }
        }
    }
}

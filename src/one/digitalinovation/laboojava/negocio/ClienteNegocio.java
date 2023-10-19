package one.digitalinovation.laboojava.negocio;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.Cliente;

import java.util.Optional;

/**
 * Classe para manipular a entidade {@link Cliente}.
 */
public class ClienteNegocio {

    /**
     * {@inheritDoc}.
     */
    private Banco bancoDados;

    /**
     * Construtor.
     *
     * @param banco Banco de dados para ter acesso aos clientes cadastrados
     */
    public ClienteNegocio(Banco banco) {
        this.bancoDados = banco;
    }

    /**
     * Consulta o cliente pelo seu CPF.
     *
     * @param cpf CPF de um cliente
     * @return O cliente que possuir o CPF passado.
     */
    public Optional<Cliente> consultar(String cpf) {

        for (Cliente cliente: bancoDados.getClientes()) {
            if (cliente.getCpf().equals(cpf)) {
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }

    public void cadastrarNovoCliente(Cliente cliente) {
        bancoDados.setCliente(cliente);
    }

    /**
     * Cadastra um novo cliente.
     * @param cliente Novo cliente que terá acesso a aplicação
     */
    //TODO Fazer a inclusão de cliente
    private void salvar(Cliente cliente) {
        bancoDados.adicionarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso.");
    }

    /**
     * Exclui um cliente específico.
     * @param cpf CPF do cliente
     */
    //TODO Fazer a exclusão de cliente
    public void excluir(String cpf) {
        for (Cliente cliente : bancoDados.getClientes()) {
            if (cliente.getCpf().equals(cpf)) {
                bancoDados.removerCliente(cliente);
                System.out.println("Cliente excluído com sucesso.");
                return;
            }
        }
        System.out.println("Cliente não encontrado.");
    }

    public void listarClientes() {
        if (bancoDados.getClientes().length == 0) {
            System.out.println("Não há clientes cadastrados.");
        } else {
            for (Cliente cliente : bancoDados.getClientes()) {
                System.out.println(cliente.toString());
            }
        }

    }

}

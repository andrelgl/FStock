package kn.fstock.fstock.models;

import java.util.List;

public class Estoque extends Base {
    private List<Pessoa> pessoaAdmin;
    private List<Pessoa> pessoas;
    private List<Produto> produtos;
    private List<Tipo> tipos;
    private List<Recipiente> recipientes;

    public List<Pessoa> getPessoaAdmin() {
        return pessoaAdmin;
    }

    public void setPessoaAdmin(List<Pessoa> pessoaAdmin) {
        this.pessoaAdmin = pessoaAdmin;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Tipo> getTipos() {
        return tipos;
    }

    public void setTipos(List<Tipo> tipos) {
        this.tipos = tipos;
    }

    public List<Recipiente> getRecipientes() {
        return recipientes;
    }

    public void setRecipientes(List<Recipiente> recipientes) {
        this.recipientes = recipientes;
    }
}




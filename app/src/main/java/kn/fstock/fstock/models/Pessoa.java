package kn.fstock.fstock.models;

import java.util.List;

public class Pessoa
{
    public int id;
    public String nome;
    public String email;
    public List<Object> estoques;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Object> getEstoques() {
        return estoques;
    }

    public void setEstoques(List<Object> estoques) {
        this.estoques = estoques;
    }
}
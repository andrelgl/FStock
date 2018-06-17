package kn.fstock.fstock.models;

import java.util.ArrayList;
import java.util.List;

import kn.fstock.fstock.fragments.ProdutoEstoqueFragment;

public class Produto extends Base {
    private double qtd_min;
    private double qtd_max;
    private double qtd;
    private int estoque_id;
    private List<Item> items = new ArrayList<>();

    public Produto(String nome, double qtd_min, double qtd_max) {
        this.nome = nome;
        this.qtd_min = qtd_min;
        this.qtd_max = qtd_max;
    }

    public double getQtd_min() {
        return qtd_min;
    }

    public void setQtd_min(double qtd_min) {
        this.qtd_min = qtd_min;
    }

    public double getQtd_max() {
        return qtd_max;
    }

    public void setQtd_max(double qtd_max) {
        this.qtd_max = qtd_max;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public int getEstoque_id() {
        return estoque_id;
    }

    public void setEstoque_id(int estoque_id) {
        this.estoque_id = estoque_id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

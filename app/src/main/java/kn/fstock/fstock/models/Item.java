package kn.fstock.fstock.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Item extends Base{
    private int produto_id;
    private double qtd;
    private Date dt_validade;

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public Date getDt_validade() {
        return dt_validade;
    }

    public void setDt_validade(Date dt_validade) {
        this.dt_validade = dt_validade;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy");
        return  formatter.format(dt_validade);
    }
}

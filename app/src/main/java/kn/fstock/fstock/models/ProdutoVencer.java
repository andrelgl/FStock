package kn.fstock.fstock.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProdutoVencer extends Base {
    private Date dt_validade;
    private int estoque_id;

    public Date getDt_validade() {
        return dt_validade;
    }

    public String getDateFormatada(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy");
        return  formatter.format(dt_validade);
    }

    public void setDt_validade(Date dt_validade) {
        this.dt_validade = dt_validade;
    }

    public int getEstoque_id() {
        return estoque_id;
    }

    public void setEstoque_id(int estoque_id) {
        this.estoque_id = estoque_id;
    }
}

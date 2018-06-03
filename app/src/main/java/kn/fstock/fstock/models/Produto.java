package kn.fstock.fstock.models;

public class Produto extends Base {
    private double quantMinima;
    private double quantMaxima;
    private double quantAtual;
    private String imagem;
    private String data;
    private Estoque estoqueId;
    private Tipo tipoId;

    public double getQuantMinima() {
        return quantMinima;
    }

    public void setQuantMinima(double quantMinima) {
        this.quantMinima = quantMinima;
    }

    public double getQuantMaxima() {
        return quantMaxima;
    }

    public void setQuantMaxima(double quantMaxima) {
        this.quantMaxima = quantMaxima;
    }

    public double getQuantAtual() {
        return quantAtual;
    }

    public void setQuantAtual(double quantAtual) {
        this.quantAtual = quantAtual;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Estoque getEstoqueId() {
        return estoqueId;
    }

    public void setEstoqueId(Estoque estoqueId) {
        this.estoqueId = estoqueId;
    }

    public Tipo getTipoId() {
        return tipoId;
    }

    public void setTipoId(Tipo tipoId) {
        this.tipoId = tipoId;
    }
}

package kn.fstock.fstock.models;

import java.util.List;

public class Tipo extends Base {
    private List<Recipiente> recipiente;

    public List<Recipiente> getRecipiente() {
        return recipiente;
    }

    public void setRecipiente(List<Recipiente> recipiente) {
        this.recipiente = recipiente;
    }
}

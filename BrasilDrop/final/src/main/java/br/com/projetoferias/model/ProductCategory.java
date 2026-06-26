package br.com.projetoferias.model;

public enum ProductCategory {
    CHUTEIRAS("Chuteiras"),
    CAMISAS("Camisas"),
    CALCAS("Calcas"),
    BERMUDAS("Bermudas"),
    BONES("Bones"),
    TENIS("Tenis"),
    ACESSORIOS("Acessorios");

    private final String label;

    ProductCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

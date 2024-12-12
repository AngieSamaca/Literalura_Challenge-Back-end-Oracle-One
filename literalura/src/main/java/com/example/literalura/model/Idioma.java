package com.example.literalura.model;

public enum Idioma {
    INGLES ("en"),
    ESPAÑOL ("es"),
    PORTUGUES("pt");
    private String idioma;

    Idioma(String idioma) {
        this.idioma = idioma;
    }

    public String getIdioma(){
        return idioma;
    }

    public static Idioma fromString(String idioma) {
        for (Idioma idiomaEnum : Idioma.values()){
            if (idiomaEnum.getIdioma().equals(idioma)){
                return idiomaEnum;
            }
        }
        return null;
    }
}

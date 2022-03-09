package com.demowebapp.model;

import java.io.Serializable;

public class Result implements Serializable {
    private Integer number;
    private int comparaison;

    public Result(Integer number, int comparaison) {
        this.number = number;
        this.comparaison = comparaison;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public int getComparaison() {
        return comparaison;
    }

    public void setComparaison(int comparaison) {
        this.comparaison = comparaison;
    }

    public String getMessage() {
        if (comparaison == 0) {
            return "Indovinato";
        } else if (comparaison < 0) {
            return "Troppo basso";
        } else {
            return "Troppo alto";
        }
    }

    public String getCssMessageClass() {
        if (comparaison == 0) {
            return "won";
        } else if (comparaison < 0) {
            return "toolow";
        } else {
            return "toohigh";
        }
    }

}

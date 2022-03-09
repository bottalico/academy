package com.example.demorest.model;

import java.io.Serializable;

public class Result implements Serializable {
    private Long number;
    private int comparaison;

    public Result(Long number, int comparaison) {
        this.number = number;
        this.comparaison = comparaison;
    }

    /**
     * @return long return the number
     */
    public Long getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(Long number) {
        this.number = number;
    }

    /**
     * @return int return the comparaison
     */
    public int getComparaison() {
        return comparaison;
    }

}

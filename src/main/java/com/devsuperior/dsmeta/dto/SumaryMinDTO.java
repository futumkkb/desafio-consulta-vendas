package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SumaryProjection;

public class SumaryMinDTO {
    private String name;
    private Double amount;

    public SumaryMinDTO(){

    }

    public SumaryMinDTO(String name, Double amount){
        this.name = name;
        this.amount = amount;
    }

    public SumaryMinDTO(SumaryProjection projection){
        this.name = projection.getName();
        this.amount = projection.getAmount();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

package com.example.citystone;
import java.util.HashMap;
import java.util.Map;

public class example {


    private String id;
    private String pracovisko;
    private String produkt;
    private String farba;
    private String pocet;
    private String stav;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPracovisko() {
        return pracovisko;
    }

    public void setPracovisko(String pracovisko) {
        this.pracovisko = pracovisko;
    }

    public String getProdukt() {
        return produkt;
    }

    public void setProdukt(String produkt) {
        this.produkt = produkt;
    }

    public String getFarba() {
        return farba;
    }

    public void setFarba(String farba) {
        this.farba = farba;
    }

    public String getPocet() {
        return pocet;
    }

    public void setPocet(String pocet) {
        this.pocet = pocet;
    }

    public String getStav() {
        return stav;
    }

    public void setStav(String stav) {
        this.stav = stav;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
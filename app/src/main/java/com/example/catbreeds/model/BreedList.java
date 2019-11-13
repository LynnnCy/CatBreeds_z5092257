package com.example.catbreeds.model;

import com.google.gson.annotations.SerializedName;

public class BreedList {
    public Weight weight;
    public String id;
    public String name;
    public String temperament;
    public String origin;
    public String description;
    @SerializedName("life_span")
    public String lifespan;
    @SerializedName("wikipedia_url")
    public String link;
    @SerializedName("dog_friendly")
    public String dogfriendly ;

    public Weight getWeight() { return weight; }

    public void setWeight(Weight weight) { this.weight = weight; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getTemperament() { return temperament; }

    public void setTemperament(String temperament) { this.temperament = temperament; }

    public String getOrigin() { return origin; }

    public void setOrigin(String origin) { this.origin = origin; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getLifespan() { return lifespan; }

    public void setLifespan(String lifespan) { this.lifespan = lifespan; }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    public String getDogfriendly() { return dogfriendly; }

    public void setDogfriendly(String dogfriendly) { this.dogfriendly = dogfriendly; }

    public class Weight {
        public String imperial;
        public String metric;

        public String getImperial() { return imperial; }

        public void setImperial(String imperial) { this.imperial = imperial; }

        public String getMetric() { return metric; }

        public void setMetric(String metric) { this.metric = metric; }
    }

}

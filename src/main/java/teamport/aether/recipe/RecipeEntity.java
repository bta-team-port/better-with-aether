package teamport.aether.recipe;

import java.util.Objects;

public class RecipeEntity {
    String entity;
    int amount = 1;

    public RecipeEntity(String entity) {
        this.entity = entity;
    }

    public RecipeEntity(String entity, int amount) {
        this.entity = entity;
        this.amount = amount;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RecipeEntity)) return false;
        RecipeEntity that = (RecipeEntity) o;
        return amount == that.amount && Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, amount);
    }

}

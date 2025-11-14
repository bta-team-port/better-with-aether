package teamport.aether.recipe;

import java.util.Objects;

public class RecipeEntity {
    private final String entity;
    private final int amount;

    @SuppressWarnings("unused")
    public RecipeEntity(String entity) {
        this(entity, 1);

    }

    public RecipeEntity(String entity, int amount) {
        this.entity = entity;
        this.amount = amount;
    }

    public String getEntity() {
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeEntity that = (RecipeEntity) o;
        return amount == that.amount && Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, amount);
    }
}

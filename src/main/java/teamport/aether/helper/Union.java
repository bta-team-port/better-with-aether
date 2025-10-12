package teamport.aether.helper;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class Union {
    private Object value;
    private final Class<?>[] allowedTypes;

    public Union(Class<?>... allowedTypes) {
        this.value = null;
        this.allowedTypes = allowedTypes;
    }

    public Union(Object value, Class<?>... allowedTypes) {
        this.value = value;
        this.allowedTypes = allowedTypes;
    }

    private boolean isPresent(Class<?> tClass) {
        return value != null && tClass.isAssignableFrom(value.getClass());
    }

    public boolean isAnyPresent() {
        return value != null;
    }

    public <T> Optional<T> of(Class<T> tClass) {
        if (isPresent(tClass)) {
            return Optional.of(tClass.cast(value));
        }
        return Optional.empty();
    }

    public void set(Union union) {
        this.set(union.value);
    }

    public void set(Object value) {
        if (value == null) {
            this.value = null;
            return;
        }

        if (Arrays.stream(allowedTypes).noneMatch(t -> t.isAssignableFrom(value.getClass()))) {
            throw new RuntimeException(
                    String.format("value \"%s\" cannot be assigned to any of: [%s]",
                            value.getClass().getCanonicalName(),
                            Arrays.stream(allowedTypes).map(Class::getCanonicalName).collect(Collectors.joining(", "))
                    )
            );
        }

        this.value = value;
    }
}

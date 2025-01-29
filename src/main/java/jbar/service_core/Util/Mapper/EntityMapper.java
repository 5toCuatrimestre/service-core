package jbar.service_core.Util.Mapper;

import java.lang.reflect.Field;

public class EntityMapper {
    public static <T, U> T merge(T existingEntity, U dto) {
        if (dto == null) {
            return existingEntity;
        }

        for (Field dtoField : dto.getClass().getDeclaredFields()) {
            try {
                dtoField.setAccessible(true);
                Object value = dtoField.get(dto);

                if (value != null) { // Solo actualizar si el valor no es nulo
                    Field entityField = existingEntity.getClass().getDeclaredField(dtoField.getName());
                    entityField.setAccessible(true);

                    // Verificar si el campo en la entidad tiene un valor diferente antes de actualizar
                    Object existingValue = entityField.get(existingEntity);
                    if (existingValue == null || !existingValue.equals(value)) {
                        entityField.set(existingEntity, value);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
                // Ignorar si el campo no existe en la entidad
            }
        }
        return existingEntity;
    }
}

package com.metroporto.enums;

public class EnumLabelConverter
{
    // Define a static method to create enum instances based on label
    public <E extends Enum<E> & LabelInterface> E fromLabel(String label, Class<E> enumClass)
    {
        for (E enumValue : enumClass.getEnumConstants()) {
            if (enumValue.getLabel().equalsIgnoreCase(label))
            {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("Invalid label for enum " + enumClass.getSimpleName() + ": " + label);
    }
}

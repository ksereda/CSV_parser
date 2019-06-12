package com.opencsv.ServiceABC.parser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {

    private String[] header;
    private String[] columnMappings;

    @Override
    public String[] generateHeader(T bean) {
        init(bean);
        super.setColumnMapping(this.columnMappings);
        return this.header;
    }

    private void init(T bean) {
        List<String> header = new ArrayList<>();
        List<String> columnMappings = new ArrayList<>();
        Field[] fields = FieldUtils.getAllFields(bean.getClass());
        for (Field field : fields) {
            CsvBindByName annotation = field.getAnnotation(CsvBindByName.class);
            if (annotation != null) {
                columnMappings.add(field.getName());
                header.add(annotation.column());
            }
        }
        this.header = header.toArray(new String[0]);
        this.columnMappings = columnMappings.toArray(new String[0]);
    }
}

package com.opencsv.ServiceABC.parser;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class CsvBeanWriter<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractCsvReader.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final ReentrantLock lock = new ReentrantLock();

    private File file;
    private Writer writer;
    private StatefulBeanToCsv<T> beanToCsv;

    protected abstract Class<T> getClassType();

    public CsvBeanWriter(File file) {
        try {
            this.file = file;
            this.writer = new BufferedWriter(new FileWriter(file));
            this.beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(getMappingStrategy())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private ColumnPositionMappingStrategy<T> getMappingStrategy() {
        CustomMappingStrategy<T> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(getClassType());
        return mappingStrategy;
    }

    protected void write(T bean) {
        write(Collections.singletonList(bean));
    }

    protected void write(List<T> beans) {
        try {
            lock.lock();
            LOGGER.info("Writing to file {} \n beans: {}", file.getAbsolutePath(), OBJECT_MAPPER.writeValueAsString(beans));
            beanToCsv.write(beans);
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            lock.unlock();
        }
    }

    protected void close() {
        try {
            writer.close();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}

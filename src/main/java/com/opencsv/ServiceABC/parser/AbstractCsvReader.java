package com.opencsv.ServiceABC.parser;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class AbstractCsvReader<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractCsvReader.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private File file;
    private String fileName;
    private int lineNumber = 1;
    private int successHandleCount;

    private Set<Integer> errorParseLines = new HashSet<>();
    private Set<Integer> errorHandleLines = new HashSet<>();

    protected abstract void handleFeedFileLine(T t);

    protected void before() {

    }

    public File getFile() {
        return file;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isHandledWithoutErrors() {
        return errorParseLines.isEmpty() && errorHandleLines.isEmpty();
    }

    public void handleFeedFile(final File file) {
        try {
            this.file = file;
            this.fileName = file.getName();
            handleFeedFile(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found {}", file);
        }
    }

    private void handleFeedFile(final InputStream is) {
        before();
        readFile(is);
        after();
    }

    private void logResults() {
        logErrorParseLines();
        logErrorHandleLines();
        logGeneralResults();
    }

    private void logGeneralResults() {
        LOGGER.info("Handled successfully : {}", successHandleCount);
        LOGGER.info("Handled with errors  : {}", errorHandleLines.size());
        LOGGER.info("Parsed with errors   : {}", errorParseLines.size());
    }

    private void logErrorParseLines() {
        if (errorParseLines.isEmpty()) {
            return;
        }

        LOGGER.info("Parse with errors lines: {}", errorParseLines);
    }

    private void logErrorHandleLines() {
        if (errorHandleLines.isEmpty()) {
            return;
        }

        LOGGER.info("Handle with errors lines: {}", errorHandleLines);
    }

    protected void after() {
        logResults();
    }

    private void readFile(final InputStream is) {
        try (Reader reader = new BufferedReader(new InputStreamReader(is))) {
            Iterator<T> fileIterator = createFileIterator(reader);
            while (fileIterator.hasNext()) {
                handleFileLine(fileIterator);
            }
        } catch (IOException e) {
            LOGGER.error("Can't handle feed file: {}", e.getMessage());
        }
    }

    private Iterator<T> createFileIterator(final Reader reader) {
        CsvToBean<T> csvBuilder = createCsvBuilder(reader);
        return csvBuilder.iterator();
    }

    private CsvToBean<T> createCsvBuilder(final Reader reader) {
        return new CsvToBeanBuilder<T>(reader)
                .withType(getClassType())
                .withIgnoreLeadingWhiteSpace(true)
                .build();
    }

    private void handleFileLine(final Iterator<T> fileIterator) {
        T fileLine = parseFileLine(fileIterator);
        if (fileLine == null) {
            LOGGER.info("Feed file line is null.");
            return;
        }
        handleFileLine(fileLine);
        lineNumber++;
    }

    private T parseFileLine(final Iterator<T> csvUserIterator) {
        try {
            T feedFileLine = csvUserIterator.next();
            LOGGER.info("Parsed feed file line {}", lineNumber);
            return feedFileLine;
        } catch (Exception e) {
            LOGGER.error("Can't parse line {}: {}", lineNumber, e.getMessage());
            errorParseLines.add(lineNumber);
            return null;
        }
    }

    private void handleFileLine(final T fileLine) {
        try {
            handleFeedFileLine(fileLine);
            successHandleCount++;
        } catch (Exception e) {
            logError(fileLine, e);
            errorHandleLines.add(lineNumber);
        }
    }

    private void logError(final T fileLine, final Exception e) {
        LOGGER.error("Can't handle line {}", lineNumber);
        logErrorData(fileLine);
        LOGGER.error(e.getMessage());
    }

    private void logErrorData(final T fileLine) {
        try {
            LOGGER.error("Data: {}", OBJECT_MAPPER.writeValueAsString(fileLine));
        } catch (JsonProcessingException e1) {
            LOGGER.error("Data: {}", fileLine);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<T> getClassType() {
        final ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) superclass.getActualTypeArguments()[0];
    }
}

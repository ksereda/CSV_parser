package com.csvparser.parser;

import com.csvparser.entity.CsvStructure;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface Parser {

    List<CsvStructure> parse() throws IOException, ParseException;
}

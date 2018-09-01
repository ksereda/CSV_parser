package com.csvparser.parser;

import java.io.IOException;
import com.csvparser.dao.Connection;


public class MainParser {

    public static void main(String[] args) throws IOException {
        Connection connection = new Connection();
        try {
            connection.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

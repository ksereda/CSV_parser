package com.csvparser.parser;

import com.csvparser.entity.CsvStructure;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvParser implements Parser{

    private String filePath = "/home/prog5/Documents/csv_example.csv";

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // Парсинг CSV файла по указанному пути и получение продуктов из него
    public List<CsvStructure> parse() throws IOException {
        //Загружаем строки из файла
        List<CsvStructure> listWithData = new ArrayList<CsvStructure>();
        List<String> fileLines = Files.readAllLines(Paths.get(filePath));
        for (String fileLine : fileLines) {
            String[] splitedText = fileLine.split(",");
            ArrayList<String> columnList = new ArrayList<String>();
            for (int i = 0; i < splitedText.length; i++) {
                //Если колонка начинается на кавычки или заканчиваеться на кавычки
                if (IsColumnPart(splitedText[i])) {
                    String lastText = columnList.get(columnList.size() - 1);
                    columnList.set(columnList.size() - 1, lastText + ","+ splitedText[i]);
                } else {
                    columnList.add(splitedText[i]);
                }
            }
            CsvStructure csvfile = new CsvStructure();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                Date parsedDate = dateFormat.parse(columnList.get(0));
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                csvfile.setExperienceDate(timestamp);
            } catch(Exception e) {
                e.getMessage();
            }
            csvfile.setCampaignId(columnList.get(1));
            csvfile.setCampaignName(columnList.get(2));
            csvfile.setUserSessionId(columnList.get(3));
            csvfile.setSource(columnList.get(4));
            csvfile.setOptIn(Boolean.valueOf(columnList.get(5)));
            csvfile.setFirstName(columnList.get(6));
            csvfile.setLastName(columnList.get(7));
            csvfile.setAddress(columnList.get(8));
            csvfile.setPhone(columnList.get(9));
            csvfile.setCity(columnList.get(10));
            csvfile.setState(columnList.get(11));
            csvfile.setZipCode(columnList.get(12));
            csvfile.setColor(columnList.get(13));
            listWithData.add(csvfile);
        }
        return listWithData;
    }

    //Проверка является ли колонка частью предыдущей колонки
    private static boolean IsColumnPart(String text) {
        String trimText = text.trim();
        //Если в тексте одна ковычка и текст на нее заканчиваеться значит это часть предыдущей колонки
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }
}

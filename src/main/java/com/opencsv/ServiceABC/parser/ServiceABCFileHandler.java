package com.opencsv.ServiceABC.parser;

import com.opencsv.ServiceABC.entity.ServiceABCFileLineEntity;
import com.opencsv.facade.ServiceABCService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServiceABCFileHandler extends AbstractCsvReader<ServiceABCFileLineEntity> {

    private static final Logger LOGGER = LogManager.getLogger(ServiceABCFileHandler.class);

    private static final String FILE_DATE_PREFIX = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

    private Map<Long, File> filesMap;
    private Map<Long, CsvBeanWriter<ExtendedServiceABCFileLine>> writersMap;

    private ServiceABCService serviceABCService;

    public ServiceABCFileHandler(ServiceABCService serviceABCService) {
        this.serviceABCService = serviceABCService;
        this.filesMap = new HashMap<>();
        this.writersMap = new HashMap<>();
    }

    @Override
    protected void handleFeedFileLine(ServiceABCFileLineEntity line) {
        line.setFileName(getFileName());
        serviceABCService.saveToDb(line);
        saveToFile(line);
    }

    private void saveToFile(ServiceABCFileLineEntity line) {
        getWriter(line.getCollegeId()).write(serviceABCService.createExtendedServiceABCFileLine(line));
    }

    private CsvBeanWriter<ExtendedServiceABCFileLine> getWriter(Long collegeId) {
        if (!writersMap.containsKey(collegeId)) {
            writersMap.put(collegeId, new CsvBeanWriter<ExtendedServiceABCFileLine>(getCollegeFile(collegeId)) {
                @Override
                protected Class<ExtendedServiceABCFileLine> getClassType() {
                    return ExtendedServiceABCFileLine.class;
                }
            });
        }

        return writersMap.get(collegeId);
    }

    private File getCollegeFile(Long collegeId) {
        if (!filesMap.containsKey(collegeId)) {
            filesMap.put(collegeId, createCollegeFile(collegeId));
        }

        return filesMap.get(collegeId);
    }

    private File createCollegeFile(Long collegeId) {
        File folder = getCollegeFolder(collegeId);
        String fileName = createFileName(collegeId);
        return createCollegeFile(folder, fileName);
    }

    private String createFileName(Long collegeId) {
//        return collegeId + "_" + FILE_DATE_PREFIX + "_" + getFileName();
        return collegeId + "_" + getFileName();
    }

    private File createCollegeFile(File folder, String fileName) {
        File file = new File(folder, fileName);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Can't create file " + file);
                }
            } catch (IOException e) {
                throw new RuntimeException("Can't create file " + file);
            }
        }
        return file;
    }

    private File getCollegeFolder(Long collegeId) {
        File folder;
        if (collegeId != null) {
            folder = new File(NEW_FILE_FTP_FOLDER, collegeId.toString());
        } else {
            folder = new File(NEW_FILE_FTP_FOLDER, "nullIdCollege");
        }
        if (!folder.exists() && !folder.mkdirs()) {
            LOGGER.error("Can't create college folder {}", folder);
        }
        return folder;
    }

    @Override
    protected void after() {
        super.after();
        closeWrites();
        if (isHandledWithoutErrors()) {
            serviceABCService.moveFileToSuccessDirectory(getFile());
        } else {
            serviceABCService.moveFileToFailDirectory(getFile());
        }
    }

    private void closeWrites() {
        if (writersMap.isEmpty()) {
            return;
        }

        waitWritingFinished();

        for (CsvBeanWriter writer : writersMap.values()) {
            writer.close();
        }
    }

    private void waitWritingFinished() {
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }
}

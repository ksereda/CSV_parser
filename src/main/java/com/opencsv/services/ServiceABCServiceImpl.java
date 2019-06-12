package com.opencsv.services;

import com.opencsv.ServiceABC.entity.ServiceABCFileLineEntity;
import com.opencsv.ServiceABC.parser.ExtendedServiceABCFileLine;
import com.opencsv.ServiceABC.parser.ServiceABCFileHandler;
import com.opencsv.dao.ServiceABCFileLineDao;
import com.opencsv.facade.ServiceABCService;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServiceABCServiceImpl implements ServiceABCService {

    private static final Logger LOGGER = LogManager.getLogger(ServiceABCServiceImpl.class);

    private static final String SERVICEABC_FTP_FOLDER = "/home/ServiceABC";
    private static final String SUCCESS_FTP_FOLDER = "/home/ServiceABC/success/";
    private static final String FAIL_FTP_FOLDER = "/home/ServiceABC/failed/";

    public static final String NEW_FILE_FTP_FOLDER = "/home/files";

    @Inject
    private ServiceABCFileLineDao serviceABCFileLineDao;

    @Inject
    private ServiceABCFileLileAssembler serviceABCFileLileAssembler;

    @Override
    public void lookUpFiles() {
        LOGGER.info("Start look up ServiceABC files");
        File[] files = searchCsvFiles();

        if (ArrayUtils.isEmpty(files)) {
            LOGGER.info("No files found to process");
            return;
        }

        LOGGER.info("Found %s files to process", files.length);
        try {
            parseFile(files);
            LOGGER.info("Files successfully processed");
        } catch (Exception e) {
            LOGGER.error("File was not parsed", e);
        }
        LOGGER.info("Finish look up ServiceABC files");
    }

    public void moveFileToSuccessDirectory(File file) {
        try {
            File successFolder = new File(SUCCESS_FTP_FOLDER);
            File successOldFile = new File(SUCCESS_FTP_FOLDER, file.getName());
            if (successOldFile.exists() && !successOldFile.delete()) {
                throw new RuntimeException("Can't delete old file " + successOldFile);
            }
            FileUtils.moveFileToDirectory(file, successFolder, true);
            LOGGER.info("File .csv was successfully moved to the success folder");
        } catch (IOException e) {
            LOGGER.info("Can't move file {} to success directory", file);
        }
    }

    public void moveFileToFailDirectory(File file) {
        try {
            File failedFolder = new File(FAIL_FTP_FOLDER);
            File failedOldFile = new File(SUCCESS_FTP_FOLDER, file.getName());
            if (failedOldFile.exists() && !failedOldFile.delete()) {
                throw new RuntimeException("Can't delete old file " + failedOldFile);
            }
            FileUtils.moveFileToDirectory(file, failedFolder, true);
            LOGGER.info("File .csv was moved to the failed folder");
        } catch (IOException e) {
            LOGGER.info("Can't move file {} to failed directory", file);
        }
    }

    private File[] searchCsvFiles() {
        return new File(SERVICEABC_FTP_FOLDER).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.toLowerCase().endsWith(".csv");
            }
        });
    }

    private void parseFile(File[] files) {
        for (File file : files) {
            parseFile(file);
        }
    }

    private void parseFile(File file) {
        new ServiceABCFileHandler(this).handleFeedFile(file);
    }

    @Transactional
    @Override
    public void saveToDb(ServiceABCFileLineEntity entity) {
        serviceABCFileLineDao.persist(entity);
    }

    @Override
    public ExtendedServiceABCFileLine createExtendedJebbitFileLine(ServiceABCFileLineEntity entity) {
        return serviceABCFileLileAssembler.assemble(entity);
    }
}

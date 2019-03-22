package com.csvparser.task;

public class SchedulerTaskImpl implements CsvService {

    private static final Logger LOGGER = LogManager.getLogger(SchedulerTaskBean.class);

    private static final String JEBBIT_FTP_FOLDER = "/home/prog5/Documents/test/";
    private static final String SUCCESS_FTP_FOLDER = "/home/prog5/Documents/test/success/";
    private static final String ERROR_FTP_FOLDER = "/home/prog5/Documents/test/error/";

    private CsvService csvService;

    @Inject
    private Parser parser;

    @Inject
    public SchedulerTaskBean(CsvService csvService) {
        this.csvService = csvService;
    }

    @Override
    public void startSchedulerTask() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                fileParsingJob();
            }
        }, 0, 30, TimeUnit.MINUTES);
    }

    private void fileParsingJob() {

        File[] files = searchCsvFiles();

        if (ArrayUtils.isNotEmpty(files)) {
            LOGGER.info("Found %s files to process", files.length);
            try {
                parseFile(files);
                LOGGER.info("Files successfully processed");
            } catch (Exception e) {
                LOGGER.error("File was not parsed", e);
            }
        } else {
            LOGGER.info("No files found to process");
        }

    }

    private File[] searchCsvFiles() {

        return new File(JEBBIT_FTP_FOLDER).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.toLowerCase().endsWith(".csv");
            }
        });

    }

    private void parseFile(File[] files) {

        for (File file : files) {

            List<CsvStructureEntity> listStructure = parser.parse(file);
            LOGGER.info("listWithData wrote in listStructure");

            if (listStructure != null) {
                try {
                    csvService.create(listStructure);
                    LOGGER.info("listStructure was successfully written to the database");
                    FileUtils.moveFileToDirectory(file, new File(SUCCESS_FTP_FOLDER), true);
                    LOGGER.info("File .csv was successfully moved to the success folder");
                } catch (Exception e) {
                    LOGGER.error("ERROR: File was not written to the database. File was moved to the error folder ", e);
                }
            } else {
                LOGGER.info("ERROR: listStructure = null");
            }

        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        fileParsingJob();
    }


}

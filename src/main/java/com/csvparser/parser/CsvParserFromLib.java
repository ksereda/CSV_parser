package com.csvparser.parser;


public class CsvParserFromLib {

    private static final Logger LOGGER = LogManager.getLogger(CsvParserFromLib.class);

    private static final String NEW_FILE_FTP_FOLDER = "/home/prog5/Documents/test/files/";
    private static final String NULL_COLLEGE_ID_FOLDER = "/home/prog5/Documents/test/files/nullIdCollege/";

    private final UserDao userDao;

    @Inject
    public CsvParserFromLib(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<CsvStructureEntity> parse(File file) {

        Map<String, List<String[]>> map = new HashMap<>();

        List<CsvStructureEntity> listWithData = new ArrayList<>();

        try {
            Reader reader = new BufferedReader(new FileReader(file));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            for (CSVRecord csvRecord : csvParser) {

                LOGGER.info("The parser read line ...");

                String experienceDate = csvRecord.get("date_created");
                String studentId = csvRecord.get("student_id");
                String organizationId = csvRecord.get("organization_id");

                setAdditionalStudentInfo(csvRecord, studentId, collegeId, map);

                String campaignName = csvRecord.get("campaign_name");
                String source = csvRecord.get("source");
                String optIn = csvRecord.get("opt_in");
                String teacherId = csvRecord.get("teacher_id");
                String decisionProcess = csvRecord.get("decision_process");

                CsvStructureEntity csvfile = new CsvStructureEntity();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'UTC'");
                Date date = format.parse(experienceDate);
                csvfile.setExperienceDate(new Timestamp(date.getTime()));

                csvfile.setStudentId(studentId);
                csvfile.setOrganizationId(organizationId);
                csvfile.setCampaignName(campaignName);
                csvfile.setSource(source);
                csvfile.setOptIn(optIn);
                csvfile.setTeacherId(teacherId);
                csvfile.setDecisionProcess(decisionProcess);

                listWithData.add(csvfile);

                LOGGER.info("File parsed. Data was added to the listWithData");
            }

            createNewFiles(map);

        } catch (Exception e) {
            LOGGER.error("ERROR: File was not parsed. Data was not added to the listWithData. Return null " + e);
        }

        return listWithData;
    }

    private void setAdditionalStudentInfo(CSVRecord csvRecord, String studentId, String collegeId, Map<String, List<String[]>> map) throws DataNotFoundException, ParseException {

        UserEntity userEntity;

        if (!studentId.isEmpty()) {

            Long studId = Long.valueOf(studentId);
            userEntity = userDao.findById(studId);

            if (userEntity instanceof StudentEntity) {
                StudentEntity studentEntity = (StudentEntity) userEntity;

                String[] arrayStudentInfo = new String[100 + csvRecord.size()];

                arrayStudentInfo[0] = collegeId;

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date substructedDate = new Date(formatter.parse(csvRecord.get(0).substring(0, csvRecord.get(0).lastIndexOf(" "))).getTime() - 4 * 3600 * 1000);
                arrayStudentInfo[1] = formatter.format(substructedDate) + " UTC";

                arrayStudentInfo[2] = studentEntity.getName();
                arrayStudentInfo[3] = studentEntity.getLastName();
                arrayStudentInfo[4] = studentEntity.getEmail();
                arrayStudentInfo[5] = studentEntity.getUsername();
                arrayStudentInfo[6] = studentEntity.getCellNumber();

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(studentEntity.getBirthDate().getTime());
                arrayStudentInfo[7] = df.format(date);

                for (int i = 0; i < 3 && i < studentEntity.getAreasInterests().size(); i++) {
                    arrayStudentInfo[8 + i] = studentEntity.getAreasInterests().get(i).getValue();
                }

                arrayStudentInfo[10] = studentEntity.getHighSchool();
                arrayStudentInfo[11] = studentEntity.getHighSchoolAddress().getState();

                toArray(csvRecord, arrayStudentInfo);

                List<String[]> list = map.containsKey(collegeId) ? map.get(collegeId) : new LinkedList<String[]>();
                list.add(arrayStudentInfo);
                map.put(collegeId, list);

            }

            LOGGER.info("Additional student's info was added ");
        }
    }

    private void createNewFiles(Map<String, List<String[]>> map) {

        for (Map.Entry<String, List<String[]>> entry : map.entrySet()) {

            String newDir = NEW_FILE_FTP_FOLDER + entry.getKey();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date date = new Date();

            try (Writer writer = new FileWriter(newDir + "  " + " Date: " + dateFormat.format(date) +
                    ".csv")) {

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("Organization ID", "Date of Submission", "Student First Name", "Student Last Name", "Email", "Username",
                                "Phone", "Date of Birth", "Areas of Interest 1", "Areas of Interest 2", "Areas of Interest 3", "School Name", "School State",
                                "student_id", "organization_id", "campaign_name", "source", "opt_in", "teacher_email", "decision_process"));

                for (String[] array : entry.getValue()) {

                    print(csvPrinter, array, 2, 12);
                    csvPrinter.flush();

                    for (File newFiles : searchNewCsvFiles()) {
                        try {
                            if (entry.getKey().isEmpty()) {
                                FileUtils.moveFileToDirectory(newFiles, new File(NULL_COLLEGE_ID_FOLDER), true);
                            } else {
                                FileUtils.moveFileToDirectory(newFiles, new File(newDir), true);
                            }
                        } catch (IOException e) {
                            LOGGER.error("ERROR: New file was not moved " + e);
                        }
                    }

                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
                LOGGER.error("ERROR: New file was not created " + ex);
            }
        }
    }

    private File[] searchNewCsvFiles() {

        File[] files = new File(NEW_FILE_FTP_FOLDER).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.toLowerCase().endsWith(".csv");
            }
        });

        assert files != null;

        if (files.length == 0) {
            LOGGER.info("No new .csv files found in folder");
        }

        return files;

    }

    private static String[] toArray(CSVRecord rec, String[] arr) {

        int i = 23;
        for (String str : rec) {
            arr[i++] = str;
        }
        return arr;

    }

    private static void print(CSVPrinter printer, String[] s, int a1, int a2) throws IOException {

        for (int b = 0; b < s.length; b++) {
            if (b >= a1 && b < a2) {
                continue;
            } else {
                printer.print(s[b]);
            }
        }

        printer.println();
    }

}

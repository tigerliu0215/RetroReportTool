package retro;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RetroReportTool {
    private static final String ORGINAL_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private final String filePath;
    private List<ALMStory> stories = new ArrayList<ALMStory>();
    private final SimpleDateFormat ORIGINAL_DATE_FORMATTER = new SimpleDateFormat(ORGINAL_DATE_PATTERN);

    public RetroReportTool(String filePath) {
        this.filePath = filePath;
    }

    public void importOriginalReport() throws IOException, ParseException {
        Reader in = new FileReader(filePath);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        for (CSVRecord record : records) {
            ALMStory story = new ALMStory();
            if(record.get(0).contains("Id")) continue;
            story.setId(record.get(0));
            story.setName(record.get(1));
            story.setState(record.get(2));
            story.setProject(record.get(4));
            story.setOwner(record.get(5));
            story.setKanbanState(record.get(7));
            story.setDevStart(parseOriginalDate(record.get(8)));
            story.setDevEnd(parseOriginalDate(record.get(9)));
            story.setDevWorkload(parseInt(record.get(10)));
            story.setTestEnd(parseOriginalDate(record.get(12)));
            story.setTestStart(parseOriginalDate(record.get(13)));
            story.setTestWorkload(parseInt(record.get(14)));
            story.setPoints(parseInt(record.get(16)));
            stories.add(story);
        }
    }

    private Date parseOriginalDate(String value) {
        try {
            return ORIGINAL_DATE_FORMATTER.parse(value);
        } catch (ParseException e) {
            return null;
        }

    }

    private int parseInt(String value) {
        if (value == null || "".equals(value) || "Unestimated".equals(value)) {
            return 0;
        }
        return Integer.parseInt(value);
    }


    public List<ALMStory> getStories() {
        return stories;
    }
}


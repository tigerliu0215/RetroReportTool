package retro;

import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class RetroReportToolTest {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    @Test
    public void should_import_csv_correctly() throws IOException, ParseException {
        String filePath = "src/test/resources/data/original_report_1.csv";
        RetroReportTool retroReportTool = new RetroReportTool(filePath);
        retroReportTool.importOriginalReport();
        List<ALMStory> stories = retroReportTool.getStories();
        ALMStory story = stories.get(0);
        assertEquals("ST185493", story.getId());
        assertEquals("Appscan script record for DSH/HRC", story.getName());
        assertEquals("Completed", story.getState());
        assertEquals("DSH", story.getProject());
        assertEquals("DENNIS WU", story.getOwner());
        assertEquals("QA Testing Completed", story.getKanbanState());
        assertNull(story.getDevStart());
        assertEqualDates("2019-01-16",story.getDevEnd());
        assertEquals(4, story.getDevWorkload());
        assertEqualDates("2019-01-14",story.getTestStart());
        assertEqualDates("2019-01-15",story.getTestEnd());
        assertEquals(0, story.getTestWorkload());
        assertEquals(1, story.getPoints());
    }

    private static void assertEqualDates(String expected, Date value) {
        DateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        String strValue = formatter.format(value);
        assertEquals(expected, strValue);
    }
}
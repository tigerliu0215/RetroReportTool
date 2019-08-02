package retro;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RetroReportTool {

  private static final String ORGINAL_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  private final String filePath;
  private List<ALMStory> stories = new ArrayList<ALMStory>();
  private List<ALMStory> doneStories = new ArrayList<ALMStory>();
  private List<ALMStory> inProgressStories = new ArrayList<ALMStory>();
  private List<ALMStory> adhocSupportStories = new ArrayList<ALMStory>();
  private List<ALMStory> remainingStory = new ArrayList<ALMStory>();
  private List<ALMStory> qaStories = new ArrayList<ALMStory>();
  private final SimpleDateFormat ORIGINAL_DATE_FORMATTER = new SimpleDateFormat(ORGINAL_DATE_PATTERN);
  private Date iterationStartDate;

  public RetroReportTool(String filePath, Date iterationStartDate) {
    this.filePath = filePath;
    this.iterationStartDate = iterationStartDate;
  }

  public void importOriginalReport() throws IOException, ParseException {
    Reader in = new FileReader(filePath);
    Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
    for (CSVRecord record : records) {
      ALMStory story = new ALMStory();
      if (record.get(0).contains("Id")) {
        continue;
      }
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
      story.setTag(record.get(15));
      story.setPoints(parseInt(record.get(16)));
      story.setRemainingStory(iterationStartDate);
      story.calculateHourCost();
      if (story.isAdhocSupport()) {
        adhocSupportStories.add(story);
      } else if (story.isQARoleStory()) {
        qaStories.add(story);
      } else if (story.isRemainingStory()) {
        remainingStory.add(story);
      } else if (story.isDone()) {
        doneStories.add(story);
      } else {
        inProgressStories.add(story);
      }
      stories.add(story);
    }
  }

  public void exportRetroReport(String directory, String fileName) throws ReportInternalException, IOException {
    List<List<Object>> objects = new LinkedList<List<Object>>();
    objects.add(constructStoryHeader("Done Story"));
    objects.addAll(constructStoriesData(this.doneStories));
    objects.add(constructEmptyLine());

    objects.add(constructStoryHeader("Remaining Story"));
    objects.addAll(constructStoriesData(this.remainingStory));
    objects.add(constructEmptyLine());

    objects.add(constructStoryHeader("In Progress Story"));
    objects.addAll(constructStoriesData(this.inProgressStories));
    objects.add(constructEmptyLine());

    objects.add(constructStoryHeader("Adhoc Support Story"));
    objects.addAll(constructStoriesData(this.adhocSupportStories));
    objects.add(constructEmptyLine());

    objects.add(constructStoryHeader("QA Story"));
    objects.addAll(constructStoriesData(this.qaStories));
    Excel2003Utils.writeExcelData(directory, fileName, "retro data", objects);
  }

  private List<List<Object>> constructStoriesData(List<ALMStory> stories) {
    List<List<Object>> storiesData = new LinkedList<List<Object>>();
    for (ALMStory story : stories) {
      List<Object> storyData = new LinkedList<Object>();
      storyData.add(story.getId());
      storyData.add(story.getName());
      storyData.add(story.getPointHours());
      storyData.add(story.getActualHours());
      storyData.add(story.getDiffHours());
      storyData.add(story.getKanbanState());
      storyData.add(story.isRemainingStory() ? "Yes" : "");
      storiesData.add(storyData);
    }
    storiesData.add(constructStoriesSummary(stories));
    return storiesData;

  }

  private List<Object> constructStoriesSummary(List<ALMStory> stories) {
    int totalPointHours = 0;
    int totalActualHours = 0;
    int totalDiffHours = 0;
    for (ALMStory story : stories) {
      totalPointHours += story.getPointHours();
      totalActualHours += story.getActualHours();
      totalDiffHours += story.getDiffHours();
    }
    List<Object> storyData = new LinkedList<Object>();
    storyData.add("");
    storyData.add("Total");
    storyData.add(totalPointHours);
    storyData.add(totalActualHours);
    storyData.add(totalDiffHours);
    storyData.add("");
    storyData.add("");
    return storyData;
  }

  private List<Object> constructStoryHeader(String type) {
    List<Object> header = new LinkedList<Object>();
    header.add("");
    header.add(type);
    header.add("估point（by hour）");
    header.add("实际（by hour）");
    header.add("diff(by hour)");
    header.add("status");
    header.add("Remaining Story");
    return header;
  }

  private List<Object> constructEmptyLine() {
    List<Object> header = new LinkedList<Object>();
    header.add("");
    header.add("");
    header.add("");
    header.add("");
    header.add("");
    header.add("");
    header.add("");
    return header;
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


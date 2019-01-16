package retro;

import sun.font.CoreMetrics;

import java.util.Date;

public class ALMStory {
    private String id;
    private String name;
    private String state;
    private String release;
    private String project;
    private String owner;
    private String kanbanState;
    private Date devStart;
    private Date devEnd;
    private int devWorkload;
    private Date testStart;
    private Date testEnd;
    private int testWorkload;
    private int points;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setKanbanState(String kanbanState) {
        this.kanbanState = kanbanState;
    }

    public void setDevStart(Date devStart) {
        this.devStart = devStart;
    }

    public void setDevEnd(Date devEnd) {
        this.devEnd = devEnd;
    }

    public void setDevWorkload(int devWorkload) {
        this.devWorkload = devWorkload;
    }

    public void setTestStart(Date testStart) {
        this.testStart = testStart;
    }

    public void setTestEnd(Date testEnd) {
        this.testEnd = testEnd;
    }

    public void setTestWorkload(int testWorkload) {
        this.testWorkload = testWorkload;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getProject() {
        return project;
    }

    public String getOwner() {
        return owner;
    }

    public String getKanbanState() {
        return kanbanState;
    }

    public Date getDevStart() {
        return devStart;
    }

    public Date getDevEnd() {
        return devEnd;
    }

    public int getDevWorkload() {
        return devWorkload;
    }

    public Date getTestStart() {
        return testStart;
    }

    public Date getTestEnd() {
        return testEnd;
    }

    public int getTestWorkload() {
        return testWorkload;
    }

    public int getPoints() {
        return points;
    }
}

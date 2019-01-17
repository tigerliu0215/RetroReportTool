package retro;

/**
 * Created by LIUWI3 on 2017-03-03.
 */
public class ReportInternalException extends Exception{
    public ReportInternalException(Exception e){
        super(e.getMessage());
    }
}

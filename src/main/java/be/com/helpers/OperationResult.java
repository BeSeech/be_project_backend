package be.com.helpers;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OperationResult
{
    public static OperationResult ok(String commentMessage)
    {
        return new OperationResult(true, "", commentMessage);
    }

    public static OperationResult ok()
    {
        return OperationResult.ok("");
    }


    public static OperationResult error(String ErrorMessage)
    {
        return new OperationResult(false, ErrorMessage, "");
    }

    private Boolean result;
    private String errorMessage;
    private String commentMessage;

    private OperationResult(Boolean result, String ErrorMessage, String commentMessage)
    {
        this.result = result;
        this.errorMessage = ErrorMessage;
        this.commentMessage = commentMessage;
    }


    public Boolean getResult()
    {
        return result;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public String getCommentMessage()
    {
        return commentMessage;
    }

    public Boolean isOk()
    {
        return result;
    }

    public Boolean isError()
    {
        return !result;
    }
}

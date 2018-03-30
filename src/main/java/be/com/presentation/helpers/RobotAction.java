package be.com.presentation.helpers;

import be.com.presentation.helpers.ActionType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RobotAction
{
    private ActionType actionType;
    private int legId;

    public ActionType getActionType()
    {
        return actionType;
    }

    public void setActionType(ActionType actionType)
    {
        this.actionType = actionType;
    }

    public int getLegId()
    {
        return legId;
    }

    public void setLegId(int legId)
    {
        this.legId = legId;
    }

    public RobotAction()
    {

    }

    public RobotAction(ActionType actionType, int legId)
    {
        setActionType(actionType);
        setLegId(legId);
    }
}

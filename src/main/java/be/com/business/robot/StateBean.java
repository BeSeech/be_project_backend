package be.com.business.robot;

import be.com.helpers.OperationResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class StateBean
{
    private String uid;
    private String name;
    private String color;

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
//    @JsonIgnore
//    private Legs legs;



    public StateBean()
    {
    }

//    @PostConstruct
//    private void init()


//    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
//    public int[] getLegSequence()

    @Override
    public String toString()
    {
        return new ReflectionToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE).toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof StateBean))
            return false;
        StateBean otherState = (StateBean)other;
        return this.toString().equals(otherState.toString());
    }
}

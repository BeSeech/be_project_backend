package be.com.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "state", schema = "PUBLIC")
@NamedQueries({
        @NamedQuery(
                name = "State.findByUid",
                query = "select o from State o where o.uid=:uid"
        ),

        @NamedQuery(
                name = "State.findAllStates",
                query = "select o from State o"
        )
})
@XmlRootElement
public class State implements Serializable
{
    @Id
    @Column(name = "uid")
    private String uid;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
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
        if (!(other instanceof State))
            return false;
        State otherState = (State)other;
        return this.toString().equals(otherState.toString());
    }
}

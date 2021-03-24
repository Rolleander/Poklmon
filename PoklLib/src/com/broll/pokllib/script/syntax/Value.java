package com.broll.pokllib.script.syntax;

public class Value
{
    private final static int NUMBER = 0, STRING = 1, BOOLEAN = 2;
    private Object value;
    private int type;

    public Value(Object value) throws VariableException
    {
        setValue(value);
    }

    public void setValue(Object value) throws VariableException
    {
        this.value = value;
        if (value instanceof Value)
        {
            Value v = (Value)value;
            this.value = v.getValue();
        }

        if (this.value instanceof Double )
        {
            type = NUMBER;
           
        }
        else if( this.value instanceof Float)
        {
        	  type = NUMBER;
        	  //cast integer to double
        	  this.value=new Double((float) this.value);
        }
        else if( this.value instanceof Integer)
        {
        	  type = NUMBER;
        	  //cast integer to double
        	  this.value=new Double((int) this.value);
        }
        else if (this.value instanceof String)
        {
            type = STRING;
        }
        else if (this.value instanceof Boolean)
        {
            type = BOOLEAN;
        }
        else
        {
            throw new VariableException();
        }
    }

    public void addValue(Value value) throws VariableException
    {
        if (value.isNumber() && this.isNumber())
        {
            setValue(this.getNumber() + value.getNumber());
        }
        else if (value.isText() && this.isText())
        {
            setValue(this.getText() + value.getText());
        }
        else
        {
            throw new VariableException();
        }
    }

    public void subValue(Value value) throws VariableException
    {
        if (value.isNumber() && this.isNumber())
        {
            setValue(this.getNumber() - value.getNumber());
        }
        else
        {
            throw new VariableException();
        }
    }

    public void mulValue(Value value) throws VariableException
    {
        if (value.isNumber() && this.isNumber())
        {
            setValue(this.getNumber() * value.getNumber());
        }
        else
        {
            throw new VariableException();
        }
    }

    public void divValue(Value value) throws VariableException
    {
        if (value.isNumber() && this.isNumber())
        {
            setValue(this.getNumber() / value.getNumber());
        }
        else
        {
            throw new VariableException();
        }
    }

    public boolean isEqual(Value value) throws VariableException
    {
        if (value.isNumber() && this.isNumber())
        {
            return this.getNumber() == value.getNumber();
        }
        else if (value.isText() && this.isText())
        {
            return this.getText().equals(value.getText());
        }
        else if (value.isBoolean() && this.isBoolean())
        {
            return this.getBoolean() == value.getBoolean();
        }
        else
        {
            throw new VariableException();
        }
    }

    public boolean isHigherThan(Value value) throws VariableException
    {
        if (value.isNumber() && this.isNumber())
        {
            return this.getNumber() > value.getNumber();
        }
        else
        {
            throw new VariableException();
        }
    }

    public boolean isLowerThan(Value value) throws VariableException
    {
        if (value.isNumber() && this.isNumber())
        {
            return this.getNumber() < value.getNumber();
        }
        else
        {
            throw new VariableException();
        }
    }

    public boolean isEqualOrHigherThan(Value value) throws VariableException
    {
        if (value.isNumber() && this.isNumber())
        {
            return this.getNumber() >= value.getNumber();
        }
        else
        {
            throw new VariableException();
        }
    }

    public boolean isEqualOrLowerThan(Value value) throws VariableException
    {
        if (value.isNumber() && this.isNumber())
        {
            return this.getNumber() < value.getNumber();
        }
        else
        {
            throw new VariableException();
        }
    }


    public double getNumber() throws VariableException
    {
        if (type == NUMBER)
        {
            return (Double)value;
        }
        throw new VariableException();
    }

    public String getText() throws VariableException
    {
        if (type == STRING)
        {
            return (String)value;
        }
        throw new VariableException();
    }

    public boolean getBoolean() throws VariableException
    {
        if (type == BOOLEAN)
        {
            return (Boolean)value;
        }
        throw new VariableException();
    }

    public boolean isNumber()
    {
        return type == NUMBER;
    }

    public boolean isText()
    {
        return type == STRING;
    }

    public boolean isBoolean()
    {
        return type == BOOLEAN;
    }

    public Object getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return value.toString();
    }
}

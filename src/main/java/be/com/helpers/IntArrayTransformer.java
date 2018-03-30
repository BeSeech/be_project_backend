package be.com.helpers;

import java.util.Arrays;

public class IntArrayTransformer
{
    public static String getString(int[] intArr, String splitter, String prefix, String appendix)
    {
        String result = prefix;

        for (int i = 0; i < intArr.length; i++) {
            if (i > 0)
                result += splitter;
            result += String.valueOf(intArr[i]);
        }

        result += appendix;
        return result;
    }

    public static int[] getArray(String stringValue, String splitter, String prefix, String appendix)
    {


        String[] items = stringValue.replace(prefix, "").replace(appendix,"").split(splitter);

        int[] result = new int[items.length];

        for (int i = 0; i < items.length; i++)
            result[i] = Integer.parseInt(items[i].trim());

        return result;
    }
}

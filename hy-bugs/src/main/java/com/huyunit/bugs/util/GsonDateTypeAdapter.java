package com.huyunit.bugs.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * User: bobo
 * Date: 13-5-8
 * Time: 下午5:39
 * To change this template use File | Settings | File Templates.
 */
public class GsonDateTypeAdapter extends TypeAdapter<Date> {

    @Override
    public void write(JsonWriter jsonWriter, Date date) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }

        jsonWriter.value(date.getTime());
    }

    @Override
    public Date read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        String datestr = jsonReader.nextString();
        return new Date(Long.parseLong(datestr));
    }

    /**
     * 字符串经过匹配转化成日期
     * @param expression
     * @return
     */
    public Date ParseToDate(String expression) {
        Pattern pat = Pattern.compile("\\([0-9]*?\\)");  //对括号进行匹配
        Matcher mat = pat.matcher(expression);
        if (mat.find()) {
            String result = mat.group(0);
            result = result.substring(1, result.length() - 1);
            Long date = Long.parseLong(result);
            return new Date(date);
        }
        return null;
    }
}

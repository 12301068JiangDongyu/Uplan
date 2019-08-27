package com.pku.system.util;



import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

public class ParseData {
    public int code;
    public String smsg;
    public String smessage;
    public String sid;

    public String parsrJson(String message){
        try {
            //JSONObject servermsg = JSONObject.parseObject(message);
            JSONTokener jsonParser = new JSONTokener(message);
            JSONObject servermsg = (JSONObject) jsonParser.nextValue();
            // JSON对象的操作
            //code=servermsg.getInt("code");
            smsg = servermsg.getString("msg");
            smessage = servermsg.getJSONObject("data").getString("message");
            System.out.println("code:"+code+" msg:"+smsg+" message:"+smessage);
        } catch (JSONException ex) {
            // 异常处理代码
        }
        return smessage;
    }
}

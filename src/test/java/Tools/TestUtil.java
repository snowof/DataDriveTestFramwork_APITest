package Tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestUtil {

    public static String getValueByJPath(JSONObject responseJson, String jPath) {
        Object obj = responseJson;
        for (String s : jPath.split("/")) {
            if (!s.isEmpty()) {
                if (!(s.contains("[") || s.contains("]"))) {
                    obj = ((JSONObject) obj).get(s);
                }else if (s.contains("[") || (s.contains("]"))) {
                    obj = ((JSONArray)((JSONObject) obj).get(s.split("\\[")[0]))
                            .get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
                }
            }
        }
        String objString = String.valueOf(obj);
        return objString;
    }
}

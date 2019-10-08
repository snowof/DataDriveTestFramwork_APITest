package ProcessExcel;

import SendRequest.runRequest;

import java.util.HashMap;

public class ProcessDataForExcel extends runRequest {

    public HashMap<String, String> processActualForExcel() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String[] splitActualMethod = null;
        if (actual.contains(";")) {
            splitActualMethod = actual.split(";");
        }
        for (int i = 0; i < splitActualMethod.length; i++) {
            String processActualLine = splitActualMethod[i];
            if (processActualLine.contains("array")) {
                String splitActualArray = processActualLine.replace("array(", "").replace(")", "");
                hashMap.put("array", splitActualArray);
            } else if (processActualLine.contains("text")) {
                String splitText = processActualLine.replace("text(", "").replace(")", "");
                hashMap.put("text", splitText);
            } else if (processActualLine.contains("pattern")) {
                String splitPattern = processActualLine.replace("pattern(", "").replace(")", "");
                hashMap.put("pattern", splitPattern);
            }
        }
        return hashMap;
    }

    public String[] processExpectedForExcel() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String[] splitExpectedMethod = null;
        if (expected.contains(",")) {
            splitExpectedMethod = expected.split(",");
        }
        return splitExpectedMethod;
    }

    public String[] processAssertForExcel() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String[] splitAssertMethod = null;
        if (assertMethodForExcel.contains(",")) {
            splitAssertMethod = assertMethodForExcel.split(",");
        }
        return splitAssertMethod;
    }
}

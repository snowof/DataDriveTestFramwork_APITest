package SendRequest;

import ProcessExcel.ProcessDataForExcel;
import ProcessExcel.ReadExcel;
import RequestMethod.RequestMethod;
import Tools.TestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class runRequest {
    private String testCaseNo;
    private String apiUrl;
    private String apiAddress;
    private String requestHeaders;
    private String requestMethod;
    private String requestBody;
    private String Response;
    private String expectedStatueCode;
    public String expected;
    public String assertMethodForExcel;
    public String actual;
    private String environmentalVariable;
    private String globalVariable;
    private String setupNo;
    private String[] requestHeaderKayAndValue;

    private CloseableHttpResponse closeableHttpResponse;
    private RequestMethod request = new RequestMethod();
    private HashMap hashMap = new HashMap();


    private ArrayList columnList;
    private ArrayList allExcelList;

    public static void main(String[] args) {
        ArrayList allExcelList;
        ArrayList columnList;
        runRequest runRequest1 = new runRequest();
        runRequest1.getDataForReadExcel();
        try {

            String[] expectedArray = null;
            String[] assertMethodForExcelArray;
            String[] actualArray = null;
            String[] environmentalVariableArray;
            String[] globalVariableArray;
            HashMap hashMap = new HashMap();
            RequestMethod request = new RequestMethod();
            AssertMethod assertMethod = new AssertMethod();
            CloseableHttpResponse closeableHttpResponse;
            allExcelList = ReadExcel.readTestCaseForExcel();
            for (int i = 0; i < allExcelList.size(); i++) {
                columnList = (ArrayList) allExcelList.get(i);
                String testCaseNo = (String) columnList.get(0);
                String apiUrl = (String) columnList.get(1);
                String apiAddress = (String) columnList.get(2);
                String requestHeaders = (String) columnList.get(3);
                String requestMethod = (String) columnList.get(4);
                String requestBody = (String) columnList.get(5);
                String Response = (String) columnList.get(6);
                String expectedStatueCode = (String) columnList.get(7);
                String expected = (String) columnList.get(8);
                String GetActualRegularExpressionForActualArray = (String) columnList.get(13);
                String GetActualArrayInJson = (String) columnList.get(11);
                String assertMethodForExcel = (String) columnList.get(9);
                String actual = (String) columnList.get(14);
                String environmentalVariable = (String) columnList.get(15);
                String globalVariable = (String) columnList.get(16);
                String[] requestHeaderKayAndValue = requestHeaders.split(",");
                /*if (expected != null || assertMethodForExcel != null || actual != null || environmentalVariable != null || globalVariable != null) {
                    expectedArray = expected.split("\n");
                    assertMethodForExcelArray = assertMethodForExcel.split("\n");
                    actualArray = actual.split("\n");
                    environmentalVariableArray = environmentalVariable.split("\n");
                    globalVariableArray = globalVariable.split("\n");
                }*/
                if (requestMethod.equals("GET") || requestMethod.equals("get")) {
                    JSONObject responseJson = runRequest1.runRequestForGet();
                    runRequest1.verify(responseJson);
                    for (int y = 0; y < expectedArray.length; i++) {
                        assertMethod.assertMethodSelect(assertMethodForExcel, expectedArray[y], actualArray[y]);
                    }
                } else if (requestMethod.equals("POST") || requestMethod.equals("post")) {
                    JSONObject responseJson = runRequest1.runRequestForPost();
                    runRequest1.verify(responseJson);
                } else if (requestMethod.equals("PUT") || requestMethod.equals("put")) {
                    JSONObject responseJson = runRequest1.runRequestForPut();
                    runRequest1.verify(responseJson);
                } else if (requestMethod.equals("Delete") || requestMethod.equals("delete")) {
                    JSONObject responseJson = runRequest1.runRequestForDelete();
                    runRequest1.verify(responseJson);
                } else if (requestMethod.equals("Patch") || requestMethod.equals("patch")) {
                    JSONObject responseJson = runRequest1.runRequestForPatch();
                    runRequest1.verify(responseJson);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataForReadExcel() {
        allExcelList = ReadExcel.readTestCaseForExcel();
        for (int i = 0; i < allExcelList.size(); i++) {
            columnList = (ArrayList) allExcelList.get(i);
            testCaseNo = (String) columnList.get(0);
            apiUrl = (String) columnList.get(1);
            apiAddress = (String) columnList.get(2);
            requestHeaders = (String) columnList.get(3);
            requestMethod = (String) columnList.get(4);
            requestBody = (String) columnList.get(5);
            Response = (String) columnList.get(6);
            expectedStatueCode = (String) columnList.get(7);
            expected = (String) columnList.get(8);
            assertMethodForExcel = (String) columnList.get(9);
            actual = (String) columnList.get(10);
            environmentalVariable = (String) columnList.get(11);
            globalVariable = (String) columnList.get(12);
            setupNo = (String) columnList.get(13);
            requestHeaderKayAndValue = requestHeaders.split(",");
            for (String text : requestHeaderKayAndValue) {
                hashMap.put(text, text);
            }
        }
    }

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


    public JSONObject runRequestForGet() throws IOException {
        String requestAddress = apiUrl + apiAddress;
        closeableHttpResponse = request.get(requestAddress);
        int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);
        Assert.assertEquals(statueCode, expectedStatueCode);
        return responseJson;
    }


    public JSONObject runRequestForPost() throws IOException {
        closeableHttpResponse = request.post(apiUrl + apiAddress, requestBody, hashMap);
        int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);
        Assert.assertEquals(statueCode, expectedStatueCode);
        return responseJson;
    }


    public JSONObject runRequestForPut() throws IOException {
        closeableHttpResponse = request.put(apiUrl + apiAddress, requestBody, hashMap);
        int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);
        Assert.assertEquals(statueCode, expectedStatueCode);
        return responseJson;
    }

    public JSONObject runRequestForDelete() throws IOException {
        closeableHttpResponse = request.delete(apiUrl + apiAddress, hashMap);
        int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);
        Assert.assertEquals(statueCode, expectedStatueCode);
        return responseJson;
    }

    public JSONObject runRequestForPatch() throws IOException {
        closeableHttpResponse = request.patch(apiUrl + apiAddress, requestBody, hashMap);
        int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);
        Assert.assertEquals(statueCode, expectedStatueCode);
        return responseJson;
    }

    public void verify(JSONObject responseJson) {
        ProcessDataForExcel processDataForExcel = new ProcessDataForExcel();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        runRequest runRequest = new runRequest();
        String[] actualArray;
        String[] textArray;
        String[] patternArray;
        String responseString;
//        hashMap = runRequest.processActualForExcel();
        hashMap = processDataForExcel.processActualForExcel();

//        String actualForResponseJson = TestUtil.getValueByJPath(responseJson, actual);
        if (hashMap.containsKey("array")) {
            String arrayActual = hashMap.get("array");
            if (arrayActual.contains(",")) {
                actualArray = arrayActual.split(",");
                for (String text : actualArray) {
                    responseString = TestUtil.getValueByJPath(responseJson, text);
                }
            } else {
                responseString = TestUtil.getValueByJPath(responseJson, arrayActual);
            }
        } else if (hashMap.containsKey("text")) {
            String arrayText = hashMap.get("text");
            if (arrayText.contains(",")) {
                textArray = arrayText.split(",");
                for (String text : textArray) {
                    responseString = TestUtil.getValueByJPath(responseJson, text);
                }
            } else {
                responseString = TestUtil.getValueByJPath(responseJson, arrayText);
            }
        } else if (hashMap.containsKey("pattern")) {
            String arrayPattern = hashMap.get("pattern");
            if (arrayPattern.contains(",")) {
                patternArray = arrayPattern.split(",");
            }
        } else {
            System.out.println("没找到Array、Text、Pattern");
        }
    }


}

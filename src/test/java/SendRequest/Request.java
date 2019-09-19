package SendRequest;

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

public class Request {
    private String testCaseNo;
    private String apiUrl;
    private String apiAddress;
    private String requestHeaders;
    private String requestMethod;
    private String requestBody;
    private String Response;
    private String expectedStatueCode;
    private String expected;
    private String assertMethodForExcel;
    private String actual;
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
                for (String text : requestHeaderKayAndValue) {
                    hashMap.put(text, text);
                }
                if (requestMethod.equals("GET") || requestMethod.equals("get")) {
                    String requestAddress = apiUrl + apiAddress;
                    closeableHttpResponse = request.get(requestAddress);
                    int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
                    String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
                    Assert.assertEquals(statueCode, expectedStatueCode);

                    JSONObject responseJson = JSON.parseObject(responseString);
//                    String JsonDetails = TestUtil.getValueByJPath(responseJson, );


                    for (int y = 0; y < expectedArray.length; i++) {
                        assertMethod.assertMethodSelect(assertMethodForExcel, expectedArray[y], actualArray[y]);
                    }
                } else if (requestMethod.equals("POST") || requestMethod.equals("post")) {
                    closeableHttpResponse = request.post(apiUrl + apiAddress, requestBody, hashMap);
                    int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
                    String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
                    Assert.assertEquals(statueCode, expectedStatueCode);


                } else if (requestMethod.equals("PUT") || requestMethod.equals("put")) {
                    closeableHttpResponse = request.put(apiUrl + apiAddress, requestBody, hashMap);
                    int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
                    String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
                    Assert.assertEquals(statueCode, expectedStatueCode);


                } else if (requestMethod.equals("Delete") || requestMethod.equals("delete")) {
                    closeableHttpResponse = request.delete(apiUrl + apiAddress, hashMap);
                    int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
                    String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());

                    Assert.assertEquals(statueCode, expectedStatueCode);


                } else if (requestMethod.equals("Patch") || requestMethod.equals("patch")) {
                    closeableHttpResponse = request.patch(apiUrl + apiAddress, requestBody, hashMap);
                    int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
                    String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
                    Assert.assertEquals(statueCode, expectedStatueCode);
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

    public void runRequestForGet() throws IOException {
        String requestAddress = apiUrl + apiAddress;
        closeableHttpResponse = request.get(requestAddress);
        int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        Assert.assertEquals(statueCode, expectedStatueCode);
        JSONObject responseJson = JSON.parseObject(responseString);
    }

    public void runRequestForPost() throws IOException {
        closeableHttpResponse = request.post(apiUrl + apiAddress, requestBody, hashMap);
        int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        Assert.assertEquals(statueCode, expectedStatueCode);
    }

    public void runRequestForPut() throws IOException {
        closeableHttpResponse = request.put(apiUrl + apiAddress, requestBody, hashMap);
        int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        Assert.assertEquals(statueCode, expectedStatueCode);
    }

    public void runRequestForDelete() throws IOException {
        closeableHttpResponse = request.delete(apiUrl + apiAddress, hashMap);
        int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);
        Assert.assertEquals(statueCode, expectedStatueCode);
    }

    public JSONObject runRequestForPatch() throws IOException {
        closeableHttpResponse = request.patch(apiUrl + apiAddress, requestBody, hashMap);
        int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);
        Assert.assertEquals(statueCode, expectedStatueCode);
        return responseJson;
    }


}

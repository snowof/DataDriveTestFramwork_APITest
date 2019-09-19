package SendRequest;

import ProcessExcel.ReadExcel;
import RequestMethod.RequestMethod;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Request {


    public static void main(String[] args) {

        try {
            ArrayList allExcelList;
            ArrayList columnList;
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
            for (int i = 0; i<allExcelList.size(); i++){
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
                String assertMethodForExcel = (String)columnList.get(9);
                String actual = (String) columnList.get(10);
                String environmentalVariable = (String) columnList.get(11);
                String globalVariable = (String) columnList.get(12);
                String[] requestHeaderKayAndValue = requestHeaders.split(",");
                if (expected != null || assertMethodForExcel != null || actual != null || environmentalVariable != null || globalVariable != null) {
                    expectedArray = expected.split("\n");
                    assertMethodForExcelArray = assertMethodForExcel.split("\n");
                    actualArray = actual.split("\n");
                    environmentalVariableArray = environmentalVariable.split("\n");
                    globalVariableArray = globalVariable.split("\n");
                }
                for (String text : requestHeaderKayAndValue) {
                    hashMap.put(text, text);
                }
                if (requestMethod.equals("GET") || requestMethod.equals("get")) {
                    String requestAddress = apiUrl +apiAddress;
                    closeableHttpResponse = request.get(requestAddress);
                    int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
                    String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
                    Assert.assertEquals(statueCode, expectedStatueCode);

                    for (int y = 0; y<expectedArray.length; i++) {
                        assertMethod.assertMethodSelect(assertMethodForExcel, expectedArray[y], actualArray[y]);
                    }
                }else if (requestMethod.equals("POST") || requestMethod.equals("post")) {
                    closeableHttpResponse = request.post(apiUrl + apiAddress, requestBody, hashMap);
                    int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
                    String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
                    Assert.assertEquals(statueCode, expectedStatueCode);


                }else if (requestMethod.equals("PUT") || requestMethod.equals("put")) {
                    closeableHttpResponse = request.put(apiUrl + apiAddress, requestBody, hashMap);
                    int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
                    String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
                    Assert.assertEquals(statueCode, expectedStatueCode);



                }else if (requestMethod.equals("Delete") || requestMethod.equals("delete")) {
                    closeableHttpResponse = request.delete(apiUrl + apiAddress, hashMap);
                    int statueCode = closeableHttpResponse.getStatusLine().getStatusCode();
                    String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());

                    Assert.assertEquals(statueCode, expectedStatueCode);


                }else if (requestMethod.equals("Patch") || requestMethod.equals("patch")) {
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
}

package SendRequest;

import org.testng.Assert;

public class AssertMethod {

    public void assertMethodSelect(String assertMethod, String expected, String actual) {
        if (assertMethod.equals("equals")) {
            Assert.assertEquals(actual, expected);
        }else if (assertMethod.equals("notEquals")) {
            Assert.assertNotEquals(actual, expected);
        }else if (assertMethod.equals("true")) {
            Assert.assertTrue(Boolean.valueOf(expected));
        }else if (assertMethod.equals("false")) {
            Assert.assertFalse(Boolean.valueOf(expected));
        }else if (assertMethod.equals("same")) {
            Assert.assertSame(actual,expected);
        }else if (assertMethod.equals("notSame")) {
            Assert.assertNotSame(actual, expected);
        }else if (assertMethod.equals("null")) {
            Assert.assertNull(expected);
        }else if (assertMethod.equals("notNull")) {
            Assert.assertNotNull(expected);
        }else if (assertMethod.equals("equalsNoOrder")) {
            String[] expectedArray = expected.split(",");
            String[] actualArray = actual.split(",");
            Assert.assertEqualsNoOrder(actualArray, expectedArray);
        }else {
            System.out.println("验证方法有：\n1.euqals：比较两者的数据值相同\n2.notEquals：比较两者数据值不相同");
        }
    }
}

package Tools;

public class StringSplit {

    public boolean split(String value, String findStr) {
        boolean isFind = false;
        String firstArray[] = value.split("}");
        for (int i = 0; i < firstArray.length; i++) {
            if (findStr == firstArray[i]) {
                isFind = true;
                break;
            } else {
                isFind = false;
            }
            String second = firstArray[i];
            String secondArray[] = second.split("}");
            if (secondArray.length != 0) {
                for (int y = 0; y < secondArray[y].length(); y++) {
                    if (findStr == secondArray[y]) {
                        isFind = true;
                        break;
                    } else {
                        isFind = false;
                    }
                }
            }


        }
        return isFind;
    }

}

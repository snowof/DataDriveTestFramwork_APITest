package Tools;

public class SkuTypeMathNum {

    public static String mathNum() {
        int num;
        while (true){
            num = (int) (1+Math.random()*(10000000));
            if (num >100000) {
                break;
            }
        }
        return String.valueOf(num);
    }
}

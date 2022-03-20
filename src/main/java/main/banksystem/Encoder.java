package main.banksystem;

public class Encoder {
    private final char key;

    public Encoder(char key) {
        this.key = key;
    }

    String Encode(String input) {
        char[] inputArray = input.toCharArray();
        char[] outputArray = new char[inputArray.length];
        for (int i = 0; i < inputArray.length; ++i) {
            outputArray[i] = (char) (inputArray[i] + key);
        }
        return String.valueOf(outputArray);
    }

    String Decode(String input) {
        char[] inputArray = input.toCharArray();
        char[] outputArray = new char[inputArray.length];
        for (int i = 0; i < inputArray.length; ++i) {
            outputArray[i] = (char) (inputArray[i] - key);
        }
        return String.valueOf(outputArray);
    }
}

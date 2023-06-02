public class ConsoleProgressBar {
    static void Loading() {
        char[] animationChars = new char[]{'|', '/', '-', '\\'};

        for (int i = 0; i <= 5; i++) {
            System.out.print("Processing " + animationChars[i % 4] + "\r");

            try {
                Thread.sleep(140);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

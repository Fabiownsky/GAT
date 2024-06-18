import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageAnalyzer {

    public static int[][] analyzeImage(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] matrix = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                matrix[y][x] = getColorCode(red, green, blue);
            }
        }

        return matrix;
    }

    private static int getColorCode(int red, int green, int blue) {
        if (red == 0 && green == 0 && blue == 0) {
            return 1; // black (muro)
        } else if (red == 255 && green == 255 && blue == 255) {
            return 0; // white (pavimento)
        } else if (red == 255 && green == 0 && blue == 0) {
            return 2; // red
        } else if (red == 255 && green == 255 && blue == 0) {
            return 3; // yellow
        } else if (red == 0 && green == 255 && blue == 0) {
            return 4; // green
        } else if (red == 0 && green == 0 && blue == 255) {
            return 5; // blue (poliziotto)
        } else if (red == 255 && green == 165 && blue == 0) {
            return 6; // orange (ladro)
        } else if (red == 139 && green == 69 && blue == 19) {
            return 7; // brown
        } else {
            return -1; // colore non specificato
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void printFirstRowRGB(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        int width = image.getWidth();

        System.out.println("RGB values of the first row:");
        for (int x = 0; x < width; x++) {
            Color color = new Color(image.getRGB(x, 0));
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            System.out.printf("(%d, %d, %d) ", red, green, blue);
        }
        System.out.println();
    }
}

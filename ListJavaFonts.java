import java.awt.GraphicsEnvironment;

public class ListJavaFonts {

  public static void ShowJavaFonts() {
    String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    for (int i = 0; i < fonts.length; i++) {
      System.out.println(fonts[i]);
    }
  }
}
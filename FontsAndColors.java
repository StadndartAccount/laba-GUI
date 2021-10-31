import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontsAndColors {
    public Font MontserratRegular;
    public Font MontserratMedium;
    public Font MontserratSemiBold;
    public Font MontserratBlack;

    public Color DarkGreen = new Color(115, 179, 189);
    public Color LightGreen = new Color(165, 214, 223);
    public Color Red = new Color(255, 194, 187);
    public Color Yellow = new Color(255, 234, 187);

    FontsAndColors() {
        try {
            MontserratRegular = Font.createFont(Font.TRUETYPE_FONT, new File("Montserrat\\Montserrat-Regular.ttf"));
            MontserratMedium = Font.createFont(Font.TRUETYPE_FONT, new File("Montserrat\\Montserrat-Medium.ttf"));
            MontserratSemiBold = Font.createFont(Font.TRUETYPE_FONT, new File("Montserrat\\Montserrat-SemiBold.ttf"));
            MontserratBlack = Font.createFont(Font.TRUETYPE_FONT, new File("Montserrat\\Montserrat-Black.ttf"));

        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

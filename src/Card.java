import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.util.Arrays;

public class Card {
    private String suit;
    private String value;
    private String imageFileName;
    private String backImageFileName;
    private boolean show;
    private BufferedImage image;
    private Rectangle cardBox;
    private boolean highlight;
    public static ArrayList<Card> DECKY;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        this.imageFileName = "images/card_"+suit+"_"+value+".png"; // Figures out the card based on the suit and value and maps to corresponding image.
        this.show = true;
        this.backImageFileName = "images/card_back.png";
        //which image should I be showing for the card? (front/back)



        this.image = readImage();
        this.cardBox = new Rectangle(-100, -100, image.getWidth(), image.getHeight());
        this.highlight = false;
    }

    public Rectangle getCardBox() {
        return cardBox;
    }

    public String getSuit() {
        return suit;
    }

    public void setRectangleLocation(int x, int y) {
        cardBox.setLocation(x, y);
    }

    public String getValue() {
        return value;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String toString() {
        return suit + " " + value;
    }

    public void flipCard() {

        // if show is true show the front otherwise show the back.
        show = !show;
        this.image = readImage();
    }




    public void flipHighlight() {
        highlight = !highlight;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage readImage() { // BufferedImage object is an object that represents an image file to be drawn on the screen
        try {
            BufferedImage image;
            if (show) {
                image = ImageIO.read(new File(imageFileName));
            }
            else {
                image = ImageIO.read(new File(backImageFileName));
            }
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public static ArrayList<Card> buildDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] values = {"02", "03", "04", "05", "06", "07", "08", "09", "10", "A", "J", "K", "Q"};
        for (String s : suits) {
            for (String v : values) {
                Card c = new Card(s, v);
                deck.add(c);
            }
        }
        Card.DECKY = deck;
        return deck;
    }

    public static ArrayList<Card> buildHand() {
        ArrayList<Card> deck = Card.buildDeck();
        ArrayList<Card> hand = new ArrayList<Card>();
        for (int i = 0; i < 9; i++) {
            int r = (int)(Math.random()*deck.size());
            Card c = deck.remove(r);
            hand.add(c);
        }
        return hand;
    }

    public static boolean canEliminate(Card a, Card b)
    {
        if (getNumValue(a) + getNumValue(b) == 11)
        {
            return true;
        }
        return false;
    }


    private static int getNumValue(Card q)
    {
        if (q.value.equals("A"))
        {
            return 1;
        }
        if (q.value.equals("J"))
        {
            return 10;
        }
        if (q.value.equals("K"))
        {
            return 10;
        }
        if (q.value.equals("Q"))
        {
            return 10;
        }
        return Integer.parseInt(q.value);
    }


}

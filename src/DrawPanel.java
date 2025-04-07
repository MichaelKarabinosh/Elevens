import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;
    // Rectangle object represents a rectangle, allows us to tell which specific one was clicked
    private Rectangle buttonPlayAgain;
    private Rectangle buttonReplaceCards;
    private ArrayList<Card> highlightedCards;

    public DrawPanel() {
        highlightedCards = new ArrayList<>();
        buttonPlayAgain = new Rectangle(167, 50, 130, 26);
        buttonReplaceCards = new Rectangle(167, 400, 160, 20);
        this.addMouseListener(this);
        hand = Card.buildHand();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 120;
        int y = 10;
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);

            if (i % 3 == 0)
            {
                y += 100;
                x = 120;
            }
            if (c.getHighlight()) {// draw the border rectangle around the card
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }
            // establish the location of the rectangle "hitbox"
            c.setRectangleLocation(x, y);

            g.drawImage(c.getImage(), x, y, null);
            x = x + c.getImage().getWidth() + 10;
        }
        // drawing the bottom button
        // with a specific font
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("PLAY AGAIN", 170, 68);
        g.drawRect((int) buttonPlayAgain.getX(), (int) buttonPlayAgain.getY(), (int) buttonPlayAgain.getWidth(), (int) buttonPlayAgain.getHeight());
        g.drawString("Cards Remaining: " + Card.DECKY.size(), 150 + 100, 28);
        g.drawRect((int) buttonReplaceCards.getX(), (int) buttonReplaceCards.getY(), (int) buttonReplaceCards.getWidth(), (int) buttonReplaceCards.getHeight());
        g.drawString("REPLACE CARDS", (int) buttonReplaceCards.getX() + 3, (int) buttonReplaceCards.getY() + 18);

    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        // left click - 1
        // right click - 3

        if (e.getButton() == 1) {
            // if clicked is inside the button rectangle
            if (buttonPlayAgain.contains(clicked)) {
                hand = Card.buildHand();
            }
            if (buttonReplaceCards.contains(clicked))
            {
                int indexFirst = 0;
                int indexLast = 0;
                System.out.println(highlightedCards);
                System.out.println(Card.canEliminate(highlightedCards.getFirst(), highlightedCards.getLast()));
                if (Card.canEliminate(highlightedCards.getFirst(), highlightedCards.getLast()))
                {
                    for (int i = 0; i < hand.size(); i++)
                    {
                        if (hand.get(i).equals(highlightedCards.getFirst()))
                        {
                            indexFirst = i;
                        }
                        if (hand.get(i).equals(highlightedCards.getLast()))
                        {
                            indexLast = i;
                        }
                    }
                    hand.set(indexFirst, Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
                    hand.set(indexLast, Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
                }
            }

            // go through each card
            // check if any of them were clicked
            // if clicked, flip that card
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            }
        }

        // right click
        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked) && hand.get(i).getHighlight()) {
                    hand.get(i).flipHighlight();
                    hand.set(i, Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
                    highlightedCards.removeLast();
                }
                else if (box.contains(clicked))
                {
                    hand.get(i).flipHighlight();
                    highlightedCards.add(hand.get(i));
                }
            }
        }


    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}
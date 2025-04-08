import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;

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
        boolean canContinue = false;
        for (int i = 0; i < hand.size(); i++)
        {
            for (int j = 0; j < hand.size() && !canContinue; j++)
            {
                if (Card.getNumValue(hand.get(i)) + Card.getNumValue(hand.get(j)) == 11 && Card.DECKY.size() > 1) {
//                    hand.set(i, Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
//                    hand.set(j, Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
                    canContinue = true;
                }
            }
        }
        if (!canContinue)
        {
            for (int i = 0; i < hand.size(); i++)
            {
                for (int j = 0; j < hand.size(); j++)
                {
                   for (int k = 0; k < hand.size() && !canContinue; k++)
                   {
                       if (allThreePresent(hand.get(i), hand.get(j), hand.get(k)) && Card.DECKY.size() > 2)
                       {
//                           hand.set(i, Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
//                           hand.set(j, Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
//                           hand.set(k, Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
                           canContinue = true;
                       }
                   }
                }
            }
        }
        g.drawRect((int) buttonReplaceCards.getX(), (int) buttonReplaceCards.getY(), (int) buttonReplaceCards.getWidth(), (int) buttonReplaceCards.getHeight());
        g.drawString("REPLACE CARDS", (int) buttonReplaceCards.getX() + 3, (int) buttonReplaceCards.getY() + 18);
        if (Card.DECKY.isEmpty())
        {
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("Courier New", Font.BOLD, 80));
            g.drawString("YOU WIN!!!", 100, 100);
            g.setColor(Color.RED);
            g.drawString("YOU WIN!!!", 150, 200);
            g.setColor(Color.BLUE);
            g.drawString("YOU WIN!!!", 50, 300);
        }
        if (!Card.DECKY.isEmpty() && !canContinue)
        {
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("Courier New", Font.BOLD, 80));
            g.drawString("YOU LOSE!!!", 100, 100);
            g.setColor(Color.RED);
            g.drawString("YOU LOSE!!!", 150, 200);
            g.setColor(Color.BLUE);
            g.drawString("YOU LOSE!!!", 50, 300);
        }

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
                if (highlightedCards.size() == 2)
                {
                    if (Card.canEliminate(highlightedCards.getFirst(), highlightedCards.getLast(), 0))
                    {
                        hand.set(hand.indexOf(highlightedCards.getFirst()), Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
                        hand.set(hand.indexOf(highlightedCards.getLast()), Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
                        highlightedCards.clear();
                    }
                }
                System.out.println(highlightedCards);
                if (highlightedCards.size() == 3) {
                    if (allThreePresent(highlightedCards.get(0), highlightedCards.get(1), highlightedCards.get(2)))
                    {
                        hand.set(hand.indexOf(highlightedCards.get(0)), Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
                        hand.set(hand.indexOf(highlightedCards.get(1)), Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
                        hand.set(hand.indexOf(highlightedCards.get(2)), Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
                        highlightedCards.clear();
                    }
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

//        if (e.getButton() == 2) {
//            for (int i = 0; i < hand.size(); i++) {
//                for (int j = 0; j < hand.size(); j++) {
//                    if (Card.getNumValue(hand.get(i)) + Card.getNumValue(hand.get(j)) == 11 && Card.DECKY.size() > 1) {
//                        highlightedCards.add(hand.get(i));
//                        highlightedCards.add(hand.get(j));
//                        hand.get(i).flipHighlight();
//                        hand.get(j).flipHighlight();
//                        break;
//                    }
//                }
//            }
//        }
//
//                for (int i = 0; i < hand.size(); i++)
//                {
//                    for (int j = 0; j < hand.size(); j++)
//                    {
//                        for (int k = 0; k < hand.size(); k++)
//                        {
//                            if (allThreePresent(hand.get(i), hand.get(j), hand.get(k)) && Card.DECKY.size() > 2)
//                            {
////                           hand.set(i, Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
////                           hand.set(j, Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
////                           hand.set(k, Card.DECKY.remove((int) (Math.random() * Card.DECKY.size())));
//                                hand.get(i).flipHighlight();
//                                hand.get(j).flipHighlight();
//                                hand.get(k).flipHighlight();
//                                break;
//
//                            }
//                        }
//                    }
//                }
//        }

        // right click
        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked) && hand.get(i).getHighlight()) {
                    hand.get(i).flipHighlight();
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


    public boolean allThreePresent(Card a, Card b, Card c)
    {
       String concat = a.getValue() + b.getValue() + c.getValue();
       if (concat.contains("J") && concat.contains("Q") && concat.contains("K"))
       {
           return true;
       }
       return false;
    }

}
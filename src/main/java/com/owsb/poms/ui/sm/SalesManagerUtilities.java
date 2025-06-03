
package com.owsb.poms.ui.sm;

import java.awt.CardLayout;
import java.util.*;
import javax.swing.*;

public class SalesManagerUtilities {
    public static void getFrame(JPanel parent, JPanel targetPage){
        CardLayout card = (CardLayout)parent.getLayout();
        card.show(parent, targetPage.getName());
    }
}

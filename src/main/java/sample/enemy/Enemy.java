package sample.enemy;

import javafx.scene.image.Image;
import sample.player.Player;
import sample.player.TypeOfPlayer;

import java.util.List;

public class Enemy extends Player {
    public Enemy(int characterID, TypeOfPlayer typeOfPlayer, List<Image> images) {
        super(characterID, typeOfPlayer, images);
    }
}

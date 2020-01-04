package sample.maps;

import sample.game.Game;
import sample.interfaces.Observer;
import sample.player.Player;

public class MapObserver implements Observer {
    public void update(int idOfPlayer) {
        for (Player player:Game.players) {
            if(player!=null)
            {
                if(player.getCharacterID()==idOfPlayer)
                {
                    switch (player.getDirection())
                    {
                        case UP:
                            player.move(0,-player.getSpeed());
                            break;
                        case DOWN:
                            player.move(0,player.getSpeed());
                            break;
                        case LEFT:
                            player.move(-player.getSpeed(),0);
                            break;
                        case RIGHT:
                            player.move(player.getSpeed(),0);
                            break;
                        case STANDING:
                            player.move(0,0);
                            break;
                    }
                }
            }

        }

    }

    @Override
    public void update() {

    }
}

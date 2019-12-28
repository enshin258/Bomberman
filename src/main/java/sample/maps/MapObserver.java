package sample.maps;

import sample.game.Game;
import sample.interfaces.Observer;

public class MapObserver implements Observer {
    @Override
    public void update() {
        switch (Game.getPlayer1().getDirection())
        {
            case UP:
                Game.getPlayer1().move(0,-Game.getPlayer1().getSpeed());
                break;
            case DOWN:
                Game.getPlayer1().move(0,Game.getPlayer1().getSpeed());
                break;
            case LEFT:
                Game.getPlayer1().move(-Game.getPlayer1().getSpeed(),0);
                break;
            case RIGHT:
                Game.getPlayer1().move(Game.getPlayer1().getSpeed(),0);
                break;
            case STANDING:
                Game.getPlayer1().move(0,0);
                break;
            default:
                break;
        }
        switch (Game.getPlayer2().getDirection())
        {
            case UP:
                Game.getPlayer2().move(0,-Game.getPlayer2().getSpeed());
                break;
            case DOWN:
                Game.getPlayer2().move(0,Game.getPlayer2().getSpeed());
                break;
            case LEFT:
                Game.getPlayer2().move(-Game.getPlayer2().getSpeed(),0);
                break;
            case RIGHT:
                Game.getPlayer2().move(Game.getPlayer2().getSpeed(),0);
                break;
            case STANDING:
                Game.getPlayer2().move(0,0);
                break;
            default:
                break;
        }
    }
}

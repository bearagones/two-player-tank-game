/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import tankgame.gameobjects.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author anthony-pc
 */
public class UserControl implements KeyListener {

    private Player user;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;
    
    public UserControl(Player user, int up, int down, int left, int right, int shoot) {
        this.user = user;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.user.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.user.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.user.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.user.toggleRightPressed();
        }
        if (keyPressed == shoot) {
            this.user.toggleShootPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.user.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.user.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.user.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.user.unToggleRightPressed();
        }
        if (keyReleased == shoot) {
            this.user.unToggleShootPressed();
        }
    }

}

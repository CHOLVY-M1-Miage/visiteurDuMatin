package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.awt.*;
import java.util.List;

public class Clear implements Command {
    private RemoteControl remoteControl;
    private List<SimpleShape> listeShapes;

    public Clear(RemoteControl remoteControl,List<SimpleShape> listeShapes) {
        this.remoteControl = remoteControl;
    }

    @Override
    public void execute() {
        this.remoteControl.clear();
    }
}

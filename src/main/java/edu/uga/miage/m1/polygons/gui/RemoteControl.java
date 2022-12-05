package edu.uga.miage.m1.polygons.gui;

import java.util.ArrayList;
import java.util.List;
import edu.uga.miage.m1.polygons.gui.commands.*;
import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class RemoteControl {

    private List<SimpleShape> listeShapes;
    protected List<Command> commands;

    public RemoteControl(List<SimpleShape> listeShapes) {
        this.listeShapes = listeShapes;
        commands = new ArrayList<Command>();
    }

    public boolean addCommand(Command command) {
        return commands.add(command);
    }

    public void play(){
        for (Command command : commands) {
            command.execute();
        }
    }

    public void undo(){
        this.commands.remove(this.commands.size() - 1);
        this.play();
    }

    public void reset() {
        commands.clear();
    }
}
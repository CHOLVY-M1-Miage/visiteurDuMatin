package edu.uga.miage.m1.polygons.gui;

import java.util.ArrayList;
import java.util.List;
import edu.uga.miage.m1.polygons.gui.commands.*;
import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class RemoteControl {

    private List<SimpleShape> listeShapes;
    protected List<Command> commands;
    private int index = 0;

    public RemoteControl(List<SimpleShape> listeShapes) {
        this.listeShapes = listeShapes;
        commands = new ArrayList<Command>();
    }

    public boolean addCommand(Command command) {
        //Supprime les commandes du forward
        for (int i = index;i < this.commands.size();i++){
            this.commands.remove(this.commands.size() - 1);
        }
        index++;
        return commands.add(command);
    }

    public void play(){
        for (int i = 0;i < index;i++){
            this.commands.get(i).execute();
        }
    }

    public void undo(){
        if (this.index > 1) {
            this.index--;
        }
        this.play();
    }

    public void forward(){
        if (this.index < this.commands.size()) {
            this.index++;
        }
        this.play();
    }

}
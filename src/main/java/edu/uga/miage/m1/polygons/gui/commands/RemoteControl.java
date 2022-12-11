package edu.uga.miage.m1.polygons.gui.commands;

import java.util.ArrayList;
import java.util.List;
import edu.uga.miage.m1.polygons.gui.commands.*;
import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import javax.swing.*;

public class RemoteControl {

    private List<SimpleShape> listeShapes;
    protected List<Command> commands;
    private JPanel m_panel;
    private int index = 0;

    public RemoteControl(List<SimpleShape> listeShapes){
        this.listeShapes = listeShapes;
        this.m_panel = null;
        commands = new ArrayList<Command>();
    }
    public RemoteControl(List<SimpleShape> listeShapes,JPanel m_panel) {
        this.listeShapes = listeShapes;
        this.m_panel = m_panel;
        commands = new ArrayList<Command>();
    }

    public boolean addCommand(Command command) {
        System.out.println("index: "+index+"/"+this.commands.size());
        //Supprime les commandes du forward
        int taille = this.commands.size();
        for (int i = index;i < taille;i++){
            System.out.println("remove: "+i);
            this.commands.remove(this.commands.size()-1);
        }
        index++;
        System.out.println("Commands: "+this.commands);
        return commands.add(command);
    }

    public void play(){
        this.listeShapes.clear();
        for (int i = 0;i < index;i++){
            System.out.println("Commands: "+this.commands.get(i));
            this.commands.get(i).execute();
        }
    }

    public void undo(){
        System.out.println("COMMAND: undo");
        if (this.index > 0) {
            this.index--;
            System.out.println("index: "+index+"/"+this.commands.size());
        }
        this.play();

    }

    public void forward(){
        System.out.println("COMMAND: forward");
        if (this.index < this.commands.size()) {
            this.index++;
            System.out.println("index: "+index+"/"+this.commands.size());
        }
        this.play();

    }

    public void load(List<SimpleShape> shapes){
        for (SimpleShape shape: shapes){
            this.addCommand(new Draw(this.m_panel,shape,this.listeShapes));
        }
    }

}
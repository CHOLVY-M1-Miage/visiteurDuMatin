package edu.uga.miage.m1.polygons.gui.commands;

import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import javax.swing.*;

public class RemoteControl {

    private List<SimpleShape> listeShapes;
    private GroupeShape groupe;
    protected List<Command> commands;
    private JPanel mPanel;
    private int index = 0;

    public RemoteControl(List<SimpleShape> listeShapes){
        this.listeShapes = listeShapes;
        this.mPanel = null;
        commands = new ArrayList<Command>();
    }
    public RemoteControl(List<SimpleShape> listeShapes,GroupeShape groupe,JPanel m_panel) {
        this.listeShapes = listeShapes;
        this.groupe = groupe;
        this.mPanel = m_panel;
        commands = new ArrayList<Command>();
    }

    public JPanel getmPanel() {
        return this.mPanel;
    }

    public List<SimpleShape> getListeShapes(){
        return this.listeShapes;
    }

    public void addCommand(Command command) {
        //System.out.println("index: "+index+"/"+this.commands.size());
        //Supprime les commandes du forward
        int taille = this.commands.size();
        for (int i = index;i < taille;i++){
            //System.out.println("remove: "+i);
            this.commands.remove(this.commands.size()-1);
        }
        index++;
        //System.out.println("Commands: "+this.commands);
        commands.add(command);
    }

    public void play(){
        this.listeShapes.clear();
        this.groupe.getShapes().clear();
        for (int i = 0;i < index;i++){
            System.out.println("Commands: "+this.commands.get(i));
            this.commands.get(i).execute();
        }
    }

    public void undo(){
        System.out.println("COMMAND: undo");
        if (this.index > 0) {
            this.index--;
            //System.out.println("index: "+index+"/"+this.commands.size());
        }
        this.play();
    }

    public void forward(){
        System.out.println("COMMAND: forward");
        if (this.index < this.commands.size()) {
            this.index++;
            //System.out.println("index: "+index+"/"+this.commands.size());
        }
        this.play();
    }

    public void load(List<SimpleShape> shapes){
        for (SimpleShape shape: shapes){
            this.addCommand(new Draw(shape,this));
        }
    }

    public void clear(){
        index = 0;
        commands.clear();
    }

}
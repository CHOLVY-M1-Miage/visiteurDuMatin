package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GroupeShape implements SimpleShape{
    private List<SimpleShape> shapes;

    public List<SimpleShape> getShapes(){
        return this.shapes;
    }

    public SimpleShape getLastShapes(){
        return this.shapes.get(this.shapes.size() - 1);
    }

    public GroupeShape(){
        this.shapes = new ArrayList<SimpleShape>();
    }

    @Override
    public void move(int deltaX,int deltaY){
        for (SimpleShape s: this.getShapes()){
            s.move(deltaX,deltaY);
        }
    }

    public void draw(JPanel m_panel) {
        this.draw(m_panel,true);
    }

    public void draw(JPanel m_panel, boolean estDansGroupe) {
        for (SimpleShape shape:this.shapes){
            shape.draw(m_panel,true);
        }
    }

    public int getX() {
        return 0;
    }

    public void setX(int x) {
    }

    public int getY() {
        return 0;
    }

    public void setY(int y) {

    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public boolean isSelect(int coordX, int coordY) {
        //
        if (this.getShapes().isEmpty()) return false;
        int i = 0;
        while ((i < getShapes().size()) && (!getShapes().get(i).isSelect(coordX,coordY))){
            i++;
        }

        return i != getShapes().size();
    }

    public void add(SimpleShape shape){
        this.shapes.add(shape);
    }

    public void remove(SimpleShape shape){
        this.shapes.remove(shape);
    }

    public SimpleShape shapeSelect(int x, int y){
        //
        if (this.getShapes().isEmpty()) return null;
        int i = 0;
        while ((i < getShapes().size()) && (!getShapes().get(i).isSelect(x,y))){
            i++;
        }

        return (i == getShapes().size())? null : getShapes().get(i);
    }

}

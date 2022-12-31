package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.visitor.Visitor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GroupeShape implements SimpleShape{
    private Color couleur;
    private List<SimpleShape> shapes;
    public GroupeShape(){
        this.couleur = Color.RED;
        this.shapes = new ArrayList<>();
    }

    public List<SimpleShape> getShapes(){
        return this.shapes;
    }
    public SimpleShape getLastShapes(){
        return this.shapes.get(this.shapes.size() - 1);
    }

    @Override
    public void move(int x,int y){
        for (SimpleShape s: this.getShapes()){
            s.move(x,y);
        }
    }

    @Override
    public void deplace(int deltaX, int deltaY){
        for (SimpleShape s: this.getShapes()){
            s.deplace(deltaX,deltaY);
        }
    }

    public void draw(JPanel mPanel) {
        this.draw(mPanel,true);
    }
    public void draw(JPanel mPanel, boolean estDansGroupe) {
        for (SimpleShape shape:this.shapes){
            if (shape.getLabel() == null){
                shape.draw(mPanel);
            }
            shape.select(this.couleur);
        }
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public void setX(int x) {
        /*Inutile pour groupe sphape*/
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setY(int y) {
        /*Inutile pour groupe sphape*/
    }

    public void setCouleur(Color couleur){
        this.couleur = couleur;
    }

    @Override
    public JLabel getLabel(){
        return null;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void select(Color couleur) {
        /*Inutile pour groupe sphape*/
    }

    public boolean isSelect(int coordX, int coordY) {
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
        if (this.getShapes().isEmpty()) return null;
        int i = 0;
        while ((i < getShapes().size()) && (!getShapes().get(i).isSelect(x,y))){
            i++;
        }

        return (i == getShapes().size())? null : getShapes().get(i);
    }

}

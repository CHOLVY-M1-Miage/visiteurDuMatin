package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.visitor.Visitor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GroupeShape implements SimpleShape{
    private Color couleur = Color.RED;
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
    public void move(int x,int y){
        int deltaX = x - this.shapes.get(0).getX();
        int deltaY = y - this.shapes.get(0).getY();
        System.out.printf("delta(%s,%s)",deltaX,deltaY);
        for (SimpleShape s: this.getShapes()){
            s.deplace(deltaX+25,deltaY+25);
        }
    }

    @Override
    public void deplace(int deltaX, int deltaY){
        for (SimpleShape s: this.getShapes()){
            s.deplace(deltaX,deltaY);
        }
    }

    public void draw(JPanel m_panel) {
        this.draw(m_panel,true);
    }
    public void draw(JPanel m_panel, boolean estDansGroupe) {
        for (SimpleShape shape:this.shapes){
            shape.select(this.couleur);
            //shape.draw(m_panel,true);
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

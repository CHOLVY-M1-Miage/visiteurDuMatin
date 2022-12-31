/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.uga.miage.m1.polygons.gui.visitor.Visitor;

import javax.swing.*;

/**
 * This inner class implements the triangle <tt>SimpleShape</tt> service.
 * It simply provides a <tt>draw()</tt> that paints a triangle.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class Triangle extends Shape implements SimpleShape {

    public Triangle(int x, int y) {
        super(x,y,"../images/icones/triangle.png");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    //Retourne le sommet haut
    private List<Integer> getA (){
        List<Integer> coord = new ArrayList<>();
        coord.add(this.getX() + 25);
        coord.add(this.getY());
        return coord;
    }
    private int getAx(){
        return this.getA().get(0);
    }
    private int getAy(){
        return this.getA().get(1);
    }

    //Retourne le sommet gauche
    private List<Integer> getB (){
        List<Integer> coord = new ArrayList<>();
        coord.add(this.getX());
        coord.add(this.getY() + 50);
        return coord;
    }
    private int getBx(){
        return this.getB().get(0);
    }
    private int getBy(){
        return this.getB().get(1);
    }

    //Retourne le sommet droite
    private List<Integer> getC (){
        List<Integer> coord = new ArrayList<>();
        coord.add(this.getX() + 50);
        coord.add(this.getY() + 50);
        return coord;
    }
    private int getCx(){
        return this.getC().get(0);
    }
    private int getCy(){
        return this.getC().get(1);
    }

    @Override
    public boolean isSelect(int coordX, int coordY){

        return (
               ( ( (getAx()) - (coordX) ) * ( (getBy()) - (coordY)) - ( (getAy()) - (coordY)) * ( (getBx()) - (coordX))) < 0 &&
               ( ( (getBx()) - (coordX) ) * ( (getCy()) - (coordY)) - ( (getBy()) - (coordY)) * ( (getCx()) - (coordX))) < 0 &&
               ( ( (getCx()) - (coordX) ) * ( (getAy()) - (coordY)) - ( (getCy()) - (coordY)) * ( (getAx()) - (coordX))) < 0
        );
    }

    @Override
    public SimpleShape shapeSelect(int x, int y){
        return this;
    }
}

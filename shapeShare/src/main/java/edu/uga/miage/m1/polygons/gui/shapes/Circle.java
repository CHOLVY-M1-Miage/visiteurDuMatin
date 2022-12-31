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
import java.util.Objects;

import edu.uga.miage.m1.polygons.gui.visitor.Visitor;

import javax.swing.*;

public class Circle extends Shape implements SimpleShape {

    public Circle(int x, int y) {
        super(x,y,"../images/icones/circle.png");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isSelect(int coordX,int coordY){
        int x = this.getX() + 25;
        int y = this.getY() + 25;
        double dist = Math.sqrt(Math.pow((coordX - x),2) + Math.pow((coordY - y),2));
        return 25.00 > dist;
    }

    @Override
    public SimpleShape shapeSelect(int x, int y){
        return this;
    }
}

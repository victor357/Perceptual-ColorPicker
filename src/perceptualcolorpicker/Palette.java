/*MIT License

Copyright (c) 2018 Olivier Vicario

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.*/
package perceptualcolorpicker;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author olivier
 */
public class Palette extends Group {

    Thumb center;
    Thumb peri1;
    Line segment1;
    Thumb peri2;
    Line segment2;
    Thumb peri3;
    Line segment3;
    Circle c0, c1, c2, c3;

    Palette() {
        center = new Thumb();
        peri1 = new Thumb();
        segment1 = new Line();
        peri2 = new Thumb();
        segment2 = new Line();
        peri3 = new Thumb();
        segment3 = new Line();
    }

    Palette(double centerX, double centerY, double peri1X, double peri1Y, double peri2X, double peri2Y, double peri3X, double peri3Y) {
        super();

        center = new Thumb(centerX, centerY);

        ChangeListener centerMoveX = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldVal, Number newVal) -> {
            peri1.setCenterX(peri1.getCenterX() + (newVal.doubleValue() - oldVal.doubleValue()));
            peri2.setCenterX(peri2.getCenterX() + (newVal.doubleValue() - oldVal.doubleValue()));
            peri3.setCenterX(peri3.getCenterX() + (newVal.doubleValue() - oldVal.doubleValue()));
        };
        ChangeListener centerMoveY = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldVal, Number newVal) -> {
            peri1.setCenterY(peri1.getCenterY() + (newVal.doubleValue() - oldVal.doubleValue()));
            peri2.setCenterY(peri2.getCenterY() + (newVal.doubleValue() - oldVal.doubleValue()));
            peri3.setCenterY(peri3.getCenterY() + (newVal.doubleValue() - oldVal.doubleValue()));
        };
        center.centerXProperty().addListener(centerMoveX);
        center.centerYProperty().addListener(centerMoveY);

        InvalidationListener thumbMove = (Observable o) -> {
            dessinCercles();
        };
        peri1 = new Thumb(peri1X, peri1Y);
        peri1.centerXProperty().addListener(thumbMove);
        peri1.centerYProperty().addListener(thumbMove);
        segment1 = new Line(centerX, centerY, peri1X, peri1Y);
        segment1.setStroke(Color.WHITE);
        segment1.setOpacity(0.5);
        segment1.startXProperty().bind(center.centerXProperty());
        segment1.startYProperty().bind(center.centerYProperty());
        segment1.endXProperty().bind(peri1.centerXProperty());
        segment1.endYProperty().bind(peri1.centerYProperty());
        this.getChildren().addAll(center, peri1, segment1);

        peri2 = new Thumb(peri2X, peri2Y);
        peri2.centerXProperty().addListener(thumbMove);
        peri2.centerYProperty().addListener(thumbMove);
        segment2 = new Line(centerX, centerY, peri2X, peri2Y);
        segment2.setStroke(Color.WHITE);
        segment2.setOpacity(0.5);
        segment2.startXProperty().bind(center.centerXProperty());
        segment2.startYProperty().bind(center.centerYProperty());
        segment2.endXProperty().bind(peri2.centerXProperty());
        segment2.endYProperty().bind(peri2.centerYProperty());
        this.getChildren().addAll(peri2, segment2);

        peri3 = new Thumb(peri3X, peri3Y);
        peri3.centerXProperty().addListener(thumbMove);
        peri3.centerYProperty().addListener(thumbMove);
        segment3 = new Line(centerX, centerY, peri3X, peri3Y);
        segment3.setStroke(Color.WHITE);
        segment3.setOpacity(0.5);
        segment3.startXProperty().bind(center.centerXProperty());
        segment3.startYProperty().bind(center.centerYProperty());
        segment3.endXProperty().bind(peri3.centerXProperty());
        segment3.endYProperty().bind(peri3.centerYProperty());
        this.getChildren().addAll(peri3, segment3);

        c0 = new Circle();
        c0.setRadius(2);
        c0.setFill(Color.TRANSPARENT);
        c0.setOpacity(0.5);
        c0.setStroke(Color.WHITE);
        c1 = new Circle();
        c1.setRadius(2);
        c1.setFill(Color.TRANSPARENT);
        c1.setOpacity(0.5);
        c1.setStroke(Color.WHITE);
        c2 = new Circle();
        c2.setRadius(2);
        c2.setFill(Color.TRANSPARENT);
        c2.setOpacity(0.5);
        c2.setStroke(Color.WHITE);
        c3 = new Circle();
        c3.setRadius(2);
        c3.setFill(Color.TRANSPARENT);
        c3.setOpacity(0.5);
        c3.setStroke(Color.WHITE);
        this.getChildren().addAll(c0, c1, c2, c3);
        dessinCercles();
    }

    void dessinCercles() {
        c1.setCenterX(peri1.getCenterX()+(peri2.getCenterX() - peri1.getCenterX())/2);
        c1.setCenterY(peri1.getCenterY()+(peri2.getCenterY() - peri1.getCenterY())/2);
        c2.setCenterX(peri2.getCenterX()+(peri3.getCenterX() - peri2.getCenterX())/2);
        c2.setCenterY(peri2.getCenterY()+(peri3.getCenterY() - peri2.getCenterY())/2);
        c3.setCenterX(peri3.getCenterX()+(peri1.getCenterX() - peri3.getCenterX())/2);
        c3.setCenterY(peri3.getCenterY()+(peri1.getCenterY() - peri3.getCenterY())/2);
        c0.setCenterX((peri3.getCenterX()+peri1.getCenterX() + peri2.getCenterX())/3);
        c0.setCenterY((peri3.getCenterY()+peri1.getCenterY() + peri2.getCenterY())/3);
    }
}

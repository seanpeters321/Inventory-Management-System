package main.java;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class Animations {
    public void errorMessage(String text, Label label) {
        label.setOpacity(0);
        label.setText(text);
        final Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(label.opacityProperty(), 1, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();

    }
}

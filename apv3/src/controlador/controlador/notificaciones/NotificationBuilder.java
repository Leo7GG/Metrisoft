package controlador.controlador.notificaciones;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.HashMap;

public class NotificationBuilder<B extends NotificationBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected NotificationBuilder() {
    }


    // ******************** Methods *******************************************
    public final static NotificationBuilder create() {
        return new NotificationBuilder();
    }

    public final B title(final String TITLE) {
        properties.put("title", new SimpleStringProperty(TITLE));
        return (B) this;
    }

    public final B message(final String MESSAGE) {
        properties.put("message", new SimpleStringProperty(MESSAGE));
        return (B) this;
    }

    public final B image(final Image IMAGE) {
        properties.put("image", new SimpleObjectProperty<>(IMAGE));
        return (B) this;
    }

    public final Notification build() {
        final Notification NOTIFICATION;
        if (properties.keySet().contains("title") && properties.keySet().contains("message") && properties.keySet().contains("image")) {
            NOTIFICATION = new Notification(((StringProperty) properties.get("title")).get(),
                    ((StringProperty) properties.get("message")).get(),
                    ((ObjectProperty<Image>) properties.get("image")).get());
        } else if (properties.keySet().contains("title") && properties.keySet().contains("message")) {
            NOTIFICATION = new Notification(((StringProperty) properties.get("title")).get(),
                    ((StringProperty) properties.get("message")).get());
        } else if (properties.keySet().contains("message") && properties.keySet().contains("image")) {
            NOTIFICATION = new Notification(((StringProperty) properties.get("message")).get(),
                    ((ObjectProperty<Image>) properties.get("image")).get());
        } else {
            throw new IllegalArgumentException("Wrong or missing parameters.");
        }
        return NOTIFICATION;
    }
}

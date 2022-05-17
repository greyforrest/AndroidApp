package ch.bbbaden.myapplication;

import java.util.ArrayList;

public class Kontakt {
    private final String id;
    private final String name;
    private final String imageURL;

    public Kontakt(String id, String name, String imageURL) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
    }

    public static ArrayList<Kontakt> getKontakt(){
        ArrayList<Kontakt> contacts = new ArrayList<>();
        contacts.add(new Kontakt("0767612103", "Grey", "https://www.gstatic.com/devrel-devsite/prod/vb4911e76f75cbf10455736b1097dbb3769b606e3b49cb0474e4669a8e63a54e6/firebase/images/touchicon-180.png"));
        contacts.add(new Kontakt("0779474547", "JoHecht", "https://www.gstatic.com/devrel-devsite/prod/vb4911e76f75cbf10455736b1097dbb3769b606e3b49cb0474e4669a8e63a54e6/firebase/images/touchicon-180.png"));
        return contacts;
    }

    public String getName(){
        return name;
    }

    public String getId() { return id; }

    public String getImageURL() {
        return imageURL;
    }
}

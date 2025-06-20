package week9;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class User {
    public String nickName, fullName;
    public ImageView profileImage;
    public ArrayList<Post> posts = new ArrayList<>();

    public User(String nickName, String fullName, ImageView profileImage) {
        this.nickName = nickName;
        this.fullName = fullName;
        this.profileImage = profileImage;
    }

    public void addPost(Post post) {
        posts.add(post);
    }
}

package com.faisaljaved.myapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class DetailVIew extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        setTitle(firebaseUser.getEmail());
        TextView title = (TextView) findViewById(R.id.text1);
        TextView subtitle = (TextView) findViewById(R.id.text2);
        ImageView imageView = (ImageView) findViewById(R.id.image_item);

        title.setText(getIntent().getExtras().getString("titlepassed"));
        subtitle.setText(getIntent().getExtras().getString("subtitlepassed"));
        Picasso.get().load(getIntent().getExtras().getString("imageIdpassed")).into(imageView);
    }
}

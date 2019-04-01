package reen.com.firebaseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.inputtitle)
    EditText inputtitle;

    @BindView(R.id.inputauthor)
    EditText inputauthor;

    @BindView(R.id.inputyear)
    EditText inputyear;

    @BindView(R.id.inputcost)
    EditText inputcost;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonsave)
   public void  save(){

        if (! new Network().isInternetAvailable()){
            Toast.makeText(this, "check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = inputtitle.getText().toString().trim();
        String author = inputauthor.getText().toString().trim();
        String cost = inputcost.getText().toString().trim();
        String year = inputyear.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || cost.isEmpty() || year.isEmpty())

        {
            Toast.makeText(this, "fill in all values", Toast.LENGTH_SHORT).show();
            return;

        }

        Map<String,String>map=new HashMap<>();
        map.put("title",title);
        map.put("author",author);
        map.put("year",year);
        map.put("cost",cost);

        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
                db.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        inputauthor.setText("");
                        inputtitle.setText("");
                        inputyear.setText("");
                        inputcost.setText("");
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "failed.try again", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @OnClick(R.id.buttonfetch)
    public void fetch()
    {
        Intent X=new Intent(this,Fetchactivity.class);
        startActivity(X);
    }



}

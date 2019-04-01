package reen.com.firebaseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fetchactivity extends AppCompatActivity {
    @BindView(R.id.books_list)
ListView books_list;

BaseAdapter adapter;


ArrayList<Book>Books_array=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);
        ButterKnife.bind(this);


        adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return Books_array.size();
            }

            @Override
            public Object getItem(int position) {
                return Books_array.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null)
                {
                    convertView= LayoutInflater.from(getBaseContext()).inflate(R.layout.book_item,null);
                }
                TextView txttitle=convertView.findViewById(R.id.textViewtitle);
                TextView txtauthor=convertView.findViewById(R.id.textViewauthor);
                TextView txtyear=convertView.findViewById(R.id.textViewyear);
                TextView txtcost=convertView.findViewById(R.id.textViewcost);
                ImageView imagedelete=convertView.findViewById(R.id.imageViewdelete);
                final Book b=Books_array.get(position);

                txttitle.setText(b.getTitle());
                txtauthor.setText(b.getAuthor());
                txtyear.setText(b.getYear());
                txtcost.setText(b.getCost());

                imagedelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseReference db=FirebaseDatabase.getInstance().getReference().getRef().child(b.id);
                        db.setValue(null);
                        Books_array.remove(b);
                        notifyDataSetChanged();
                    }
                });
                return convertView;

            }

        };
        books_list.setAdapter(adapter);
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s :dataSnapshot.getChildren()) {
                    Book K = s.getValue(Book.class);
                    K.id=s.getKey();
                    Books_array.add(K);

                }
                adapter.notifyDataSetChanged();//refresh
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Fetchactivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

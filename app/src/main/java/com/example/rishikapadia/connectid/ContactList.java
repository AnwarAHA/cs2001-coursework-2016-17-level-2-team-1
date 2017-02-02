package com.example.rishikapadia.connectid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class ContactList extends AppCompatActivity{

    private GestureDetectorCompat gestureObject;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactlist);

        String[] names = {"Bob","Ben","Bill","Mike","Jo","Sam"};
        ListAdapter nameAdapter = new ContactAdapter(this,names);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(nameAdapter);

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());



        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String name = String.valueOf(parent.getItemAtPosition(position));
                        Intent intent = new Intent(view.getContext(),ContactProfile.class);
                        startActivityForResult(intent,0);
                        overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);
                    }

                }
        );




    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if(e2.getX()>e1.getX()){

                Intent intent = new Intent(ContactList.this,MainActivity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);

            }
            else if (e2.getX()<e1.getX()){

            }


            return true;
        }
    }


}


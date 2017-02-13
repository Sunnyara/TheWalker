package sundanllc.thewalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Sunnara on 2/6/2017.
 */

public class CreateMenu extends AppCompatActivity {

    private Button cAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);

        cAdd = (Button) findViewById(R.id.create_add);
        cAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateMenu.this,CreateAddOption.class));
            }
        });

    }
}

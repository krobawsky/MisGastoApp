package tecsup.rberrospi.laboratorio04.misgastoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REGISTER_FORM_REQUEST = 100;

    private ListView list;
    private TextView total;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        total = (TextView) findViewById(R.id.total);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
    }

    public void addItem(View view){
        startActivityForResult(new Intent(this, RegisterActivity.class), REGISTER_FORM_REQUEST);
    }

    // return from RegisterActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // refresh data
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) list.getAdapter();
        adapter.clear();

        GastoRepository gastoRepository = GastoRepository.getInstance();
        final List<Gasto> gastos = gastoRepository.getGastos();
        double suma=0;

        for (Gasto gasto : gastos) {

            suma = suma+gasto.getMonto();
            total.setText("S./ " + suma);

            adapter.add(gasto.getDetalle());
            alertDialog = new AlertDialog.Builder(this).create();

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {

                    alertDialog.setTitle("Detalle del gasto");
                    alertDialog.setMessage(""+gastos.get(posicion));
                    // Alert dialog button
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
        }
    }

}


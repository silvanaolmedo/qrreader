package ar.edu.ub.qrcodereader.barcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import ar.edu.ub.qrcodereader.R;

public class BarcodeInformationActivity extends Activity {

    private TextView barcodeResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_information);

        barcodeResult = findViewById(R.id.barcode_information);

        Intent intent = getIntent();

        String schedule = intent.getStringExtra("Schedule");

        barcodeResult.setText(schedule);
    }
}

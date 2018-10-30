package ar.edu.ub.qrcodereader.barcode;

import android.content.Context;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

public class BarcodeTracker extends Tracker<Barcode>{

    private BarcodeUpdateListener listener;

    public interface BarcodeUpdateListener {

        void onBarcodeDetected(final Barcode barcode);

        void onBarcodeNoLongerPresent();
    }

    private BarcodeTracker(BarcodeUpdateListener listener) {
        this.listener = listener;
    }

    @Override
    public void onNewItem(int i, Barcode barcode) {
        if(barcode.displayValue != null){
            this.listener.onBarcodeDetected(barcode);
        }
    }

    @Override
    public void onMissing(Detector.Detections<Barcode> detections) {
        this.listener.onBarcodeNoLongerPresent();
    }

    @Override
    public void onDone() {
        this.listener.onBarcodeNoLongerPresent();
    }

    public static class Builder{
        private BarcodeUpdateListener listener;

        public Builder(Context context){
            if (context instanceof BarcodeUpdateListener){
                this.listener = (BarcodeUpdateListener) context;
            } else{
                throw new RuntimeException("Hosting activity must implement BarcodeUpdateListener");
            }
        }

        public BarcodeTracker build(){
            return new BarcodeTracker(this.listener);
        }
    }
}

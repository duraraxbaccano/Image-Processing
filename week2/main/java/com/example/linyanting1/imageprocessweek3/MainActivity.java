package com.example.linyanting1.imageprocessweek3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {
    private Bitmap bmp;
    private ImageView img;
    private ImageView destImg;
    private RadioButton quanBtn;
    private RadioHandler handler = new RadioHandler();
    private BitmapDrawable  abmp;
    private BitmapDrawable  qbmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.sourceImg);
        abmp = (BitmapDrawable)img.getDrawable();
        destImg = (ImageView) findViewById(R.id.destImg);
        qbmp = (BitmapDrawable)destImg.getDrawable();
        quanBtn = (RadioButton) findViewById(R.id.quantization);
        quanBtn.setOnClickListener(handler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void quantization() {

        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                final double numOfLevel = Math.pow(2.0, 1.0);
                final double levelGap = 256 / numOfLevel;
                bmp = abmp.getBitmap();
                Bitmap operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());

                for(int i=0; i<bmp.getWidth(); i++){
                    for(int j=0; j<bmp.getHeight(); j++){
                        double p = (double)bmp.getPixel(i, j);
//                        int qp = (int) Math.ceil( ((p / levelGap) * levelGap - 1) );

                        operation.setPixel(i, j, (int)(p + 100.0) );
                    }
                }
                destImg.setImageBitmap(operation);
            }
        });

        worker.run();
        /*
            % Loop over number of bits
            for numOfBit = 1 : 8
                % Quantize to given number of bits
                numOfLevel = 2.^ numOfBit;
                levelGap = 256 / numOfLevel;
                quantizedImg = uint8(ceil(img / levelGap) * levelGap - 1); % quantization

                % Plot image
                subplot(2, 4, 9 - numOfBit), imshow(quantizedImg);
                if numOfBit == 1
                    name = [num2str(numOfBit) '-bit'];
                else
                    name = [num2str(numOfBit) '-bits'];
                end
                title(name);

                % Save image
                imwrite(quantizedImg, ['Quantization_' name '.png'] );
            end %end numOfBit
        */
    }

    class RadioHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.quantization:
                    quantization();
                    break;
            }
        }
    }
}

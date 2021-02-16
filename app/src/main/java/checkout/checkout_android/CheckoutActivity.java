package checkout.checkout_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import java.text.NumberFormat;
import java.util.Locale;

public class CheckoutActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initItemUI("Simple Bike", Constants.PAYMENT_AMOUNT, R.drawable.bike);

        findViewById(R.id.button_buy).setOnClickListener(this::onBuyButtonClicked);
    }

    private void initItemUI(String name, long price, @DrawableRes int imageResource) {
        TextView itemName = findViewById(R.id.text_item_name);
        ImageView itemImage = findViewById(R.id.image_item_image);
        TextView itemPrice = findViewById(R.id.text_item_price);

        itemName.setText(name);
        itemImage.setImageResource(imageResource);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        itemPrice.setText(numberFormat.format((double) price / 100));
    }

    private void onBuyButtonClicked(View view) {
        startActivity(new Intent(this, DemoActivity.class));
    }
}

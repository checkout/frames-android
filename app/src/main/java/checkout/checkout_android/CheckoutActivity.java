package checkout.checkout_android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import java.text.NumberFormat;
import java.util.Locale;

public class CheckoutActivity extends Activity {
	private Button cvvToknizationButton;
	private Button cardTokenizationButton;

	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);

		cvvToknizationButton = findViewById(R.id.button_cvv_tokenization);
		cardTokenizationButton = findViewById(R.id.button_buy);

		initItemUI("Simple Bike", Constants.PAYMENT_AMOUNT, R.drawable.bike, cardTokenizationButton);

		findViewById(R.id.button_buy).setOnClickListener(this::onBuyButtonClicked);

		cvvToknizationButton.setOnClickListener(v -> {
			startActivity(new Intent(this, CVVTokenizationActivity.class));
		});
	}

	private void initItemUI(String name, long price, @DrawableRes int imageResource, Button cvvToknizationButton) {
		TextView itemName = findViewById(R.id.text_item_name);
		ImageView itemImage = findViewById(R.id.image_item_image);
		itemName.setText(name);
		itemImage.setImageResource(imageResource);
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
		cvvToknizationButton.setText(getText(R.string.button_buy) + numberFormat.format((double) price / 100));
	}

	private void onBuyButtonClicked(View view) {
		startActivity(new Intent(this, DemoActivity.class));
	}
}

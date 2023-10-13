package checkout.checkout_android;

import static checkout.checkout_android.Constants.PUBLIC_KEY_CVV_TOKENIZATION;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.checkout.base.model.CardScheme;
import com.checkout.base.model.Environment;
import com.checkout.frames.cvvinputfield.CVVComponentApiFactory;
import com.checkout.frames.cvvinputfield.api.CVVComponentApi;
import com.checkout.frames.cvvinputfield.api.CVVComponentMediator;
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig;
import com.checkout.frames.cvvinputfield.style.DefaultCVVInputFieldStyle;
import com.checkout.frames.model.CornerRadius;
import com.checkout.frames.model.Shape;
import com.checkout.frames.style.component.base.ContainerStyle;
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle;
import com.checkout.frames.style.component.base.InputFieldStyle;
import com.checkout.frames.style.component.base.TextStyle;
import com.checkout.tokenization.model.CVVTokenDetails;
import com.checkout.tokenization.model.CVVTokenizationResultHandler;

import checkout.checkout_android.viewmodels.CVVTokenizationViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class CVVTokenizationActivity extends ComponentActivity {
	private Button amexCVVTokenizationButton;
	private Button cvvTokenizationButton;
	private LinearLayout cvvComponentLinearLayout;
	private LinearLayout amexCustomCVVComponentLinearLayout;
	private Function1<CVVTokenizationResultHandler, Unit> resultHandler;
	private CVVTokenizationViewModel cvvTokenizationViewModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cvv_tokenization);
		initViews();

		cvvTokenizationViewModel = new ViewModelProvider(this).get(CVVTokenizationViewModel.class);

		setupObservers();

		// Create cvvComponentApi
		CVVComponentApi cvvComponentApi = CVVComponentApiFactory.create(PUBLIC_KEY_CVV_TOKENIZATION, Environment.SANDBOX, this);

		// initialise CVVTokenizationResultHandler for tokenization
		resultHandler = result -> {
			if (result instanceof CVVTokenizationResultHandler.Success) {
				CVVTokenDetails tokenDetails = ((CVVTokenizationResultHandler.Success) result).getTokenDetails();
				displayTokenResultDialog(tokenDetails.getToken(), "Token Created Successfully");
			} else if (result instanceof CVVTokenizationResultHandler.Failure) {
				String errorMessage = ((CVVTokenizationResultHandler.Failure) result).getErrorMessage();
				displayTokenResultDialog(errorMessage, "Token Failure");
			}
			return Unit.INSTANCE;
		};

		createCVVComponentMediator(cvvComponentApi);

		createAMEXCVVComponentMediator(cvvComponentApi);
	}

	private void setupObservers() {
		cvvTokenizationViewModel.getIsEnteredAMEXCVVValid().observe(this, isCVVValid -> {
			if (isCVVValid)
				amexCVVTokenizationButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
			else {
				amexCVVTokenizationButton.setBackgroundColor(getResources().getColor(R.color.colorGray, this.getTheme()));
			}
			amexCVVTokenizationButton.setEnabled(isCVVValid);
		});
	}

	private void initViews() {
		amexCVVTokenizationButton = findViewById(R.id.btnAmexCVVTokenization);
		cvvTokenizationButton = findViewById(R.id.btnCVVTokenization);
		cvvComponentLinearLayout = findViewById(R.id.linearCVVComponent);
		amexCustomCVVComponentLinearLayout = findViewById(R.id.linearAMEXCustomCVVComponent);
		cvvTokenizationButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
	}

	private void createCVVComponentMediator(CVVComponentApi cvvComponentApi) {
		// Create config for CVV component
		final CVVComponentConfig visaCVVComponentConfig = new CVVComponentConfig(
				CardScheme.Companion.fromString("unknown"),
				isEnteredCVVValid -> Unit.INSTANCE,
				DefaultCVVInputFieldStyle.INSTANCE.create()
		);

		// Create CVVComponentMediator for CVV component
		final CVVComponentMediator defaultCVVComponentMediator = cvvComponentApi.createComponentMediator(visaCVVComponentConfig);
		final View defaultCVVComponentView = defaultCVVComponentMediator.provideCvvComponentContent(cvvComponentLinearLayout);

		// Add defaultCVVComponent as view in parent layout
		cvvComponentLinearLayout.addView(defaultCVVComponentView);

		cvvTokenizationButton.setOnClickListener(v -> defaultCVVComponentMediator.createToken(resultHandler));
	}


	private void createAMEXCVVComponentMediator(CVVComponentApi cvvComponentApi) {
		final CVVComponentConfig visaCVVComponentConfig = new CVVComponentConfig(
				CardScheme.Companion.fromString("American_express"),
				isEnteredCVVValid -> {
					cvvTokenizationViewModel.setIsAmexCVVValid(isEnteredCVVValid);
					return Unit.INSTANCE;
				},
				new InputFieldStyle(new TextStyle(), "Enter cvv",
						null, new TextStyle(),
						new ContainerStyle(Constants.backgroundColor
								, Shape.RoundCorner,
								new CornerRadius(9)),
						new InputFieldIndicatorStyle.Underline()
				)
		);

		final CVVComponentMediator amexCVVComponentMediator = cvvComponentApi.createComponentMediator(visaCVVComponentConfig);

		final View amexCVVComponentView = amexCVVComponentMediator.provideCvvComponentContent(amexCustomCVVComponentLinearLayout);
		amexCustomCVVComponentLinearLayout.addView(amexCVVComponentView);

		amexCVVTokenizationButton.setOnClickListener(v -> amexCVVComponentMediator.createToken(resultHandler));
	}


	private void displayTokenResultDialog(String message, String title) {
		new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message)
				.setCancelable(false)
				.setNeutralButton("Ok", (dialog, id) -> dialog.dismiss())
				.show();
	}

}

package checkout.checkout_android.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CVVTokenizationViewModel extends ViewModel {

	private final MutableLiveData<Boolean> isEnteredAMEXCVVValid = new MutableLiveData<>();

	public MutableLiveData<Boolean> getIsEnteredAMEXCVVValid() {
		return isEnteredAMEXCVVValid;
	}

	public void setIsAmexCVVValid(Boolean isCVVValid) {
		isEnteredAMEXCVVValid.setValue(isCVVValid);
	}
}
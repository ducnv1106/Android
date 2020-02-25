package com.ducnv1106.message.view.acitivity.editcontact;

import com.ducnv1106.message.adapter.ContactAdapter;

public interface EditContactListener extends ContactAdapter.ContacListener {

    void onAddOtherPhoneClicked();

    void onAddOtherWebsiteCliked();

    void onAddOtherEmailClicked();

    void onSavePhoneClicked();

    void onCancelPhoneClicked();

    void onDeletePhoneClicked(String item, String nameData);

    void onSaveWebsiteClicked();

    void onCancelWebsiteClicked();

    void onSaveEmailClicked();

    void onCancelEmailClicked();

    void onSaveClicked();

    void onCancelClicked();

}

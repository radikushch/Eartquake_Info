package com.udacity.radik.earthquake_info.View.SettingsActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

import com.udacity.radik.earthquake_info.Presenter.ISettingsPresenter;
import com.udacity.radik.earthquake_info.Presenter.SettingsPresenter;
import com.udacity.radik.earthquake_info.R;

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        Preference.OnPreferenceChangeListener, ISettingsFragment {


    private ISettingsPresenter settingsPresenter;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_screen);

        settingsPresenter = new SettingsPresenter();
        settingsPresenter.onAttachFragment(this);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        CheckBoxPreference checkBoxPreference =
                (CheckBoxPreference) findPreference(getString(R.string.load_countries_info_key));
        if(sharedPreferences.getBoolean(checkBoxPreference.getKey(), true)) {
            settingsPresenter.loadCountriesInfoFromTheInternet();
        }else {
            settingsPresenter.loadCountriesInfoFromTheDatabase();
        }
        settingsPresenter.setAllPreferencesSummary(sharedPreferences, preferenceScreen);
    }


    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen()
                .getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen()
                .getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        settingsPresenter.onDetachFragment();
    }

    @Override
    public void showNumberInputError() {
        Toast.makeText(getActivity(), "Input number between 0 and 10", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDateInputError() {
        Toast.makeText(getActivity(), "Input date in such format: YYYY:MM:DD", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getMagnitudeKey() {
        return getString(R.string.magnitude_key);
    }

    @Override
    public String getFirstDateKey() {
        return getString(R.string.time_start_key);
    }

    @Override
    public String getLastDateKey() {
        return getString(R.string.time_end_key);
    }


    @Override
    public void setListPreference(String[] listOfCountries) {
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.location_key));
        listPreference.setEntries(listOfCountries);
        listPreference.setEntryValues(listOfCountries);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        settingsPresenter.setPreferenceSummary(preference, sharedPreferences);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return settingsPresenter.isCorrectInput(preference, newValue);
    }
}

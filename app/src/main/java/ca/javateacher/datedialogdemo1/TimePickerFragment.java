package ca.javateacher.datedialogdemo1;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import static ca.javateacher.datedialogdemo1.Constants.DATE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener{

  interface TimeSetListener{
    void onTimeSet(int hourOfDay, int minute);
  }
  private TimeSetListener mTimeSetListener;

  public TimePickerFragment() {
    // Required empty public constructor
  }

  public static TimePickerFragment getInstance(Date date){
    TimePickerFragment fragment = new TimePickerFragment();
    Bundle arguments = new Bundle();
    arguments.putSerializable(DATE_KEY, date);
    fragment.setArguments(arguments);
    return fragment;
  }

  @Override
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current time as the default values for the picker

    Date date;
    Bundle arguments = getArguments();
    if(arguments != null){
      date = (Date) arguments.getSerializable(DATE_KEY);
    }else{
      date = new Date();
    }

    final Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);

    // Create a new instance of TimePickerDialog and return it
    return new TimePickerDialog(getActivity(), this, hour, minute,
        DateFormat.is24HourFormat(getActivity()));
  }


  @Override
  public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    if(mTimeSetListener != null){
      mTimeSetListener.onTimeSet(hourOfDay, minute);
    }
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if(context instanceof TimeSetListener){
      mTimeSetListener = (TimeSetListener) context;
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mTimeSetListener = null;
  }
}

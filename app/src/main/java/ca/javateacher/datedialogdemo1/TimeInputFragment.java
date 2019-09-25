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
public class TimeInputFragment extends DialogFragment{

  interface TimeInputListener {
    void setTime(int hourOfDay, int minute);
  }
  private TimeInputListener mTimeInputListener;

  public TimeInputFragment() {
    // Required empty public constructor
  }

  public static TimeInputFragment getInstance(Date date){
    TimeInputFragment fragment = new TimeInputFragment();
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
    return new TimePickerDialog(getActivity(), this::setTime, hour, minute,
        DateFormat.is24HourFormat(getActivity()));
  }

  public void setTime(TimePicker view, int hourOfDay, int minute) {
    if(mTimeInputListener != null){
      mTimeInputListener.setTime(hourOfDay, minute);
    }
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if(context instanceof TimeInputListener){
      mTimeInputListener = (TimeInputListener) context;
    }else{
      throw new RuntimeException(context.toString()
              + " must implement TimeInputFragment.TimeInputListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mTimeInputListener = null;
  }
}

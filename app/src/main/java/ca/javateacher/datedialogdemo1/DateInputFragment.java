package ca.javateacher.datedialogdemo1;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import static ca.javateacher.datedialogdemo1.Constants.DATE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateInputFragment extends DialogFragment{

  interface DateInputListener {
    void setDate(int year, int month, int dayOfMonth);
  }
  private DateInputListener mDateInputListener;

  public DateInputFragment() {
    // Required empty public constructor
  }

  public static DateInputFragment getInstance(Date date){
    DateInputFragment fragment = new DateInputFragment();
    Bundle arguments = new Bundle();
    arguments.putSerializable(DATE_KEY, date);
    fragment.setArguments(arguments);
    return fragment;
  }

  @SuppressWarnings("ConstantConditions")
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

    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    // Create a new instance of TimePickerDialog and return it
    return new DatePickerDialog(getActivity(), this::setDate, year, month, day);
  }

  public void setDate(DatePicker view, int year, int month, int day) {
    if(mDateInputListener != null){
      mDateInputListener.setDate(year, month, day);
    }
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if(context instanceof DateInputListener){
      mDateInputListener = (DateInputListener) context;
    }else{
      throw new RuntimeException(context.toString()
              + " must implement DateInputFragment.DateInputListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mDateInputListener = null;
  }
}

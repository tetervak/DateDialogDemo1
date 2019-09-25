package ca.javateacher.datedialogdemo1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import static ca.javateacher.datedialogdemo1.Constants.*;

public class MainActivity extends AppCompatActivity
    implements
        TimeInputFragment.TimeInputListener,
        DateInputFragment.DateInputListener {

  private Date mDate;

  private TextView mTimeView;
  private TextView mDateView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if(savedInstanceState != null){
      mDate = (Date) savedInstanceState.getSerializable(DATE_KEY);
    }else{
      mDate = new Date();
    }

    mTimeView = findViewById(R.id.time_value);
    mDateView = findViewById(R.id.date_value);
    mTimeView.setText(DateFormat.getTimeFormat(this).format(mDate));
    mDateView.setText(DateFormat.getLongDateFormat(this).format(mDate));
    mTimeView.setOnClickListener(v -> showTimeInput());
    mDateView.setOnClickListener(v -> showDateInput());

    ImageButton editTimeButton = findViewById(R.id.edit_time_button);
    editTimeButton.setOnClickListener(v -> showTimeInput());

    ImageButton editDateButton = findViewById(R.id.edit_date_button);
    editDateButton.setOnClickListener(v -> showDateInput());
  }

  private void showDateInput() {
    DialogFragment fragment = DateInputFragment.getInstance(mDate);
    fragment.show(getSupportFragmentManager(), DATE_INPUT_FRAGMENT_TAG);
  }

  private void showTimeInput() {
    DialogFragment fragment = TimeInputFragment.getInstance(mDate);
    fragment.show(getSupportFragmentManager(), TIME_INPUT_FRAGMENT_TAG);
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
    if (id == R.id.about) {
      showAbout();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void showAbout() {
    AboutFragment aboutFragment = AboutFragment.newInstance();
    aboutFragment.show(getSupportFragmentManager(), ABOUT_FRAGMENT_TAG);
  }

  @Override
  public void setTime(int hour, int minute) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(mDate);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    calendar.set(year, month, day, hour, minute);
    mDate = calendar.getTime();
    mTimeView.setText(DateFormat.getTimeFormat(this).format(mDate));
  }

  @Override
  public void setDate(int year, int month, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(mDate);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    calendar.set(year, month, day, hour, minute);
    mDate = calendar.getTime();
    mDateView.setText(DateFormat.getLongDateFormat(this).format(mDate));
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(Constants.DATE_KEY, mDate);
  }

}

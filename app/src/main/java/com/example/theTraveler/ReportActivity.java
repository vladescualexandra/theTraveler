package com.example.theTraveler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theTraveler.async.Callback;
import com.example.theTraveler.databases.service.SpendingService;
import com.example.theTraveler.databases.service.VisitService;

import org.json.JSONException;

public class ReportActivity extends AppCompatActivity {

    TextView minRating;
    TextView maxRating;
    TextView avgRating;
    TextView minSpending;
    TextView maxSpending;
    TextView avgSpending;
    TextView totalSpending;

    private VisitService visitService;
    private SpendingService spendingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initComponents();
        getDataFromDB();
    }


    private void initComponents() {
        minRating = findViewById(R.id.report_ratings_min);
        maxRating = findViewById(R.id.report_ratings_max);
        avgRating = findViewById(R.id.report_ratings_avg);
        minSpending = findViewById(R.id.report_spendings_min);
        maxSpending = findViewById(R.id.report_spendings_max);
        avgSpending = findViewById(R.id.report_spendings_avg);
        totalSpending = findViewById(R.id.report_spendings_total);

        ScrollView layout = findViewById(R.id.activity_report);
        AccountActivity.changeTheme(layout, setTheme());
    }


    private boolean setTheme() {
        return getSharedPreferences(AccountActivity.SETTINGS, MODE_PRIVATE)
                .getBoolean(AccountActivity.THEME, false);
    }


    private void getDataFromDB() {
        visitService = new VisitService(getApplicationContext());
        spendingService = new SpendingService(getApplicationContext());

        String user = getSharedPreferences(StartActivity.USER_KEY, MODE_PRIVATE).getString(StartActivity.ID, null);

        try {
            visitService.getMin(user, getMinRatingFromDBCallback());
            visitService.getMax(user, getMaxRatingFromDBCallback());
            visitService.getAvg(user, getAvgRatingFromDBCallback());

            spendingService.getMin(user, getMinSpendingFromDBCallback());
            spendingService.getMax(user, getMaxSpendingFromDBCallback());
            spendingService.getAvg(user, getAvgSpendingFromDBCallback());
            spendingService.getTotal(user, getTotalSpendingFromDBCallback());
        } catch (Error e) {
            Log.e("services", e.getMessage());
        }
    }


    private Callback<Integer> getMinRatingFromDBCallback() {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUIThread(Integer result) throws JSONException {
                if (result != null) {
                    minRating.setText(getString(R.string.report_rating_min, result));
                } else {
                    minRating.setText(getString(R.string.report_rating_min, -1));
                    Toast.makeText(getApplicationContext(),
                            R.string.report_no_visits,
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        };
    }

    private Callback<Integer> getMaxRatingFromDBCallback() {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUIThread(Integer result) throws JSONException {
                if (result != null) {
                    maxRating.setText(getString(R.string.report_rating_max, result));
                } else {
                    maxRating.setText(getString(R.string.report_rating_max, -1));
                    Toast.makeText(getApplicationContext(),
                            R.string.report_no_visits,
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        };
    }

    private Callback<Integer> getAvgRatingFromDBCallback() {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUIThread(Integer result) throws JSONException {
                if (result != null) {
                    avgRating.setText(getString(R.string.report_rating_avg, result));
                } else {
                    avgRating.setText(getString(R.string.report_rating_avg, -1));
                }
            }
        };
    }

    private Callback<Float> getMinSpendingFromDBCallback() {
        return new Callback<Float>() {
            @Override
            public void runResultOnUIThread(Float result) throws JSONException {
                if (result != null) {
                    minSpending.setText(getString(R.string.report_spending_min, result));
                } else {
                    minSpending.setText(getString(R.string.report_spending_min, 0.0f));
                }
            }
        };
    }

    private Callback<Float> getMaxSpendingFromDBCallback() {
        return new Callback<Float>() {
            @Override
            public void runResultOnUIThread(Float result) throws JSONException {
                if (result != null) {
                    maxSpending.setText(getString(R.string.report_spending_max, result));
                } else {
                    maxSpending.setText(getString(R.string.report_spending_max, 0.0f));
                    Toast.makeText(getApplicationContext(),
                            R.string.report_no_visits,
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        };
    }


    private Callback<Float> getAvgSpendingFromDBCallback() {
        return new Callback<Float>() {
            @Override
            public void runResultOnUIThread(Float result) throws JSONException {
                if (result != null) {
                    avgSpending.setText(getString(R.string.report_spending_avg, result));
                } else {
                    avgSpending.setText(getString(R.string.report_spending_avg, 0.0f));
                }
            }
        };
    }

    private Callback<Float> getTotalSpendingFromDBCallback() {
        return new Callback<Float>() {
            @Override
            public void runResultOnUIThread(Float result) throws JSONException {
                if (result != null) {
                    totalSpending.setText(getString(R.string.report_spending_total, result));
                } else {
                    totalSpending.setText(getString(R.string.report_spending_total, 0.0f));
                }
            }
        };
    }
}

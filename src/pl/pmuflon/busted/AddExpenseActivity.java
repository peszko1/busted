package pl.pmuflon.busted;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.pmuflon.busted.DaoMaster;
import pl.pmuflon.busted.DaoSession;
import pl.pmuflon.busted.Expense;
import pl.pmuflon.busted.ExpenseDao;
import pl.pmuflon.busted.DaoMaster.DevOpenHelper;

import pl.pmuflon.busted.R;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddExpenseActivity extends Activity {

    private SQLiteDatabase db;


    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ExpenseDao expenseDao;
    private EditText nameField;
    private EditText amountField;
    private EditText dateField;
    private Spinner plannedField;
    private TextView alertText;
    private SimpleDateFormat sdf;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.add_expense);
	    
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "busted-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        expenseDao = daoSession.getExpenseDao();

        nameField = (EditText) findViewById(R.id.editText1);
        amountField = (EditText) findViewById(R.id.editText2);
        dateField = (EditText) findViewById(R.id.editText3);
        plannedField = (Spinner) findViewById(R.id.spinner1);
        alertText = (TextView) findViewById(R.id.alert_message);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        String expenseName = getIntent().getStringExtra("expense_name");
        String expenseAmount = getIntent().getStringExtra("expense_amount");
        if(expenseName != null) {
        	nameField.setText(expenseName);
        }
        if(expenseAmount != null) {
        	amountField.setText(expenseAmount);
        }
        
	    Button button = (Button) findViewById(R.id.addExpenseButton);
	    
	    button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(amountField.getText().toString().isEmpty()
						|| nameField.getText().toString().isEmpty()
						|| dateField.getText().toString().isEmpty()){
					alertText.setText("Wpisz nazwę, kwotę i datę.");
				}else{
					alertText.setText("");
					Expense expense = new Expense();
					expense.setAmount(Float.valueOf(amountField.getText().toString()));
					expense.setName(nameField.getText().toString());
					try {
						expense.setDate(sdf.parse(dateField.getText().toString()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					expenseDao.insert(expense);
					
					finish();					
				}
			}
		});
	
	    // TODO Auto-generated method stub
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		dateField.setText(sdf.format(new Date()));
	}
}

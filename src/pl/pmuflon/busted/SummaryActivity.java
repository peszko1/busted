package pl.pmuflon.busted;

import pl.pmuflon.busted.DaoMaster.DevOpenHelper;
import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;

public class SummaryActivity extends Activity {

    private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private ExpenseDao expenseDao;
	private InvoiceDao invoiceDao;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);
        
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "busted-db", null);
        db = helper.getReadableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        expenseDao = daoSession.getExpenseDao();
        invoiceDao = daoSession.getInvoiceDao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.summary, menu);
        return true;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	updateBalance();
    }

	private void updateBalance() {
		
	}
}

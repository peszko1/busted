package pl.pmuflon.busted;

import java.util.List;

import pl.pmuflon.busted.DaoMaster;
import pl.pmuflon.busted.DaoSession;
import pl.pmuflon.busted.Expense;
import pl.pmuflon.busted.ExpenseDao;
import pl.pmuflon.busted.DaoMaster.DevOpenHelper;
import pl.pmuflon.busted.ExpenseDao.Properties;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import pl.pmuflon.busted.R;

import de.greenrobot.dao.QueryBuilder;

public class ExpensesActivity extends Activity {

    private SQLiteDatabase db;


    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ExpenseDao expenseDao;
    private ListView expensesList;
    private List<Expense> expenses;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses_list);
        
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "busted-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        expenseDao = daoSession.getExpenseDao();
        expensesList = (ListView) findViewById(R.id.listView1);
                
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AddExpenseActivity.class);
				startActivity(intent);
			}
		});
        
        expensesList.setTextFilterEnabled(true);
        expensesList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				Intent intent = new Intent(arg1.getContext(), AddExpenseActivity.class);
				intent.putExtra("expense_name", expenses.get(position).getName());
				intent.putExtra("expense_amount", "" + expenses.get(position).getAmount());
				startActivity(intent);
			}
		});
        
        registerForContextMenu(expensesList);
        
        
    }
    
    protected void onResume(){
    	super.onResume();        
    	reloadExpensesList();
    }

	private void reloadExpensesList() {
		QueryBuilder<Expense> qb = expenseDao.queryBuilder()
    			.orderDesc(Properties.Date)
    			.orderDesc(Properties.Id)
    			.limit(20);
        expenses = qb.list();
        expensesList.setAdapter(new ArrayAdapter<Expense>(this, R.layout.list_item, expenses));
	}
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    									ContextMenuInfo menuInfo){
    	super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                Expense expense = expenses.get((int) info.id);
                expense.delete();
                reloadExpensesList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
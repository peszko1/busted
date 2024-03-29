package pl.pmuflon.busted;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.IdentityScopeType;

import pl.pmuflon.busted.Expense;
import pl.pmuflon.busted.PlannedExpense;
import pl.pmuflon.busted.Invoice;
import pl.pmuflon.busted.PlannedInvoice;

import pl.pmuflon.busted.ExpenseDao;
import pl.pmuflon.busted.PlannedExpenseDao;
import pl.pmuflon.busted.InvoiceDao;
import pl.pmuflon.busted.PlannedInvoiceDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig expenseDaoConfig;
    private final DaoConfig plannedExpenseDaoConfig;
    private final DaoConfig invoiceDaoConfig;
    private final DaoConfig plannedInvoiceDaoConfig;

    private final ExpenseDao expenseDao;
    private final PlannedExpenseDao plannedExpenseDao;
    private final InvoiceDao invoiceDao;
    private final PlannedInvoiceDao plannedInvoiceDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        expenseDaoConfig = daoConfigMap.get(ExpenseDao.class).clone();
        expenseDaoConfig.initIdentityScope(type);

        plannedExpenseDaoConfig = daoConfigMap.get(PlannedExpenseDao.class).clone();
        plannedExpenseDaoConfig.initIdentityScope(type);

        invoiceDaoConfig = daoConfigMap.get(InvoiceDao.class).clone();
        invoiceDaoConfig.initIdentityScope(type);

        plannedInvoiceDaoConfig = daoConfigMap.get(PlannedInvoiceDao.class).clone();
        plannedInvoiceDaoConfig.initIdentityScope(type);

        expenseDao = new ExpenseDao(expenseDaoConfig, this);
        plannedExpenseDao = new PlannedExpenseDao(plannedExpenseDaoConfig, this);
        invoiceDao = new InvoiceDao(invoiceDaoConfig, this);
        plannedInvoiceDao = new PlannedInvoiceDao(plannedInvoiceDaoConfig, this);

        registerDao(Expense.class, expenseDao);
        registerDao(PlannedExpense.class, plannedExpenseDao);
        registerDao(Invoice.class, invoiceDao);
        registerDao(PlannedInvoice.class, plannedInvoiceDao);
    }
    
    public void clear() {
        expenseDaoConfig.getIdentityScope().clear();
        plannedExpenseDaoConfig.getIdentityScope().clear();
        invoiceDaoConfig.getIdentityScope().clear();
        plannedInvoiceDaoConfig.getIdentityScope().clear();
    }

    public ExpenseDao getExpenseDao() {
        return expenseDao;
    }

    public PlannedExpenseDao getPlannedExpenseDao() {
        return plannedExpenseDao;
    }

    public InvoiceDao getInvoiceDao() {
        return invoiceDao;
    }

    public PlannedInvoiceDao getPlannedInvoiceDao() {
        return plannedInvoiceDao;
    }

}

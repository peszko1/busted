package pl.pmuflon.busted;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.SqlUtils;
import de.greenrobot.dao.Query;
import de.greenrobot.dao.QueryBuilder;

import pl.pmuflon.busted.Expense;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EXPENSE.
*/
public class ExpenseDao extends AbstractDao<Expense, Long> {

    public static final String TABLENAME = "EXPENSE";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Amount = new Property(2, float.class, "amount", false, "AMOUNT");
        public final static Property Date = new Property(3, java.util.Date.class, "date", false, "DATE");
        public final static Property Planned = new Property(4, Long.class, "planned", false, "PLANNED");
        public final static Property Expenses = new Property(5, Long.class, "expenses", false, "EXPENSES");
    };

    private DaoSession daoSession;

    private Query<Expense> plannedExpense_ExpenseListQuery;

    public ExpenseDao(DaoConfig config) {
        super(config);
    }
    
    public ExpenseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String sql = "CREATE TABLE " + (ifNotExists? "IF NOT EXISTS ": "") + "'EXPENSE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT NOT NULL ," + // 1: name
                "'AMOUNT' REAL NOT NULL ," + // 2: amount
                "'DATE' INTEGER NOT NULL ," + // 3: date
                "'PLANNED' INTEGER," + // 4: planned
                "'EXPENSES' INTEGER);"; // 5: expenses
        db.execSQL(sql);
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EXPENSE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Expense entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindDouble(3, entity.getAmount());
        stmt.bindLong(4, entity.getDate().getTime());
 
        Long planned = entity.getPlanned();
        if (planned != null) {
            stmt.bindLong(5, planned);
        }
    }

    @Override
    protected void attachEntity(Expense entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Expense readEntity(Cursor cursor, int offset) {
        Expense entity = new Expense( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.getFloat(offset + 2), // amount
            new java.util.Date(cursor.getLong(offset + 3)), // date
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4) // planned
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Expense entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setAmount(cursor.getFloat(offset + 2));
        entity.setDate(new java.util.Date(cursor.getLong(offset + 3)));
        entity.setPlanned(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
     }
    
    @Override
    protected Long updateKeyAfterInsert(Expense entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Expense entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "expenseList" to-many relationship of PlannedExpense. */
    public synchronized List<Expense> _queryPlannedExpense_ExpenseList(Long expenses) {
        if (plannedExpense_ExpenseListQuery == null) {
            QueryBuilder<Expense> queryBuilder = queryBuilder();
            queryBuilder.where(Properties.Expenses.eq(expenses));
            plannedExpense_ExpenseListQuery = queryBuilder.build();
        } else {
            plannedExpense_ExpenseListQuery.setParameter(0, expenses);
        }
        return plannedExpense_ExpenseListQuery.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getPlannedExpenseDao().getAllColumns());
            builder.append(" FROM EXPENSE T");
            builder.append(" LEFT JOIN PLANNED_EXPENSE T0 ON T.'PLANNED'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Expense loadCurrentDeep(Cursor cursor, boolean lock) {
        Expense entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        PlannedExpense plannedExpense = loadCurrentOther(daoSession.getPlannedExpenseDao(), cursor, offset);
        entity.setPlannedExpense(plannedExpense);

        return entity;    
    }

    public Expense loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Expense> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Expense> list = new ArrayList<Expense>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Expense> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Expense> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}

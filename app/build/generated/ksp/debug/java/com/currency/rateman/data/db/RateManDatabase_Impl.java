package com.currency.rateman.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.currency.rateman.data.db.dao.CurrencyRateDao;
import com.currency.rateman.data.db.dao.CurrencyRateDao_Impl;
import com.currency.rateman.data.db.dao.RateProviderDao;
import com.currency.rateman.data.db.dao.RateProviderDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RateManDatabase_Impl extends RateManDatabase {
  private volatile PlaygroundDao _playgroundDao;

  private volatile RateProviderDao _rateProviderDao;

  private volatile CurrencyRateDao _currencyRateDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `playground` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `address` TEXT NOT NULL, `lat` REAL NOT NULL, `lon` REAL NOT NULL, `imageUrl` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `rate_providers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `baseCurrency` TEXT NOT NULL, `phoneNumber` TEXT NOT NULL, `type` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `currency_rates` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `providerId` INTEGER NOT NULL, `foreignCurrency` TEXT NOT NULL, `buyRate` REAL NOT NULL, `sellRate` REAL NOT NULL, `date` TEXT NOT NULL, FOREIGN KEY(`providerId`) REFERENCES `rate_providers`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd364092a6f6098aa97b396b66279ea9a')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `playground`");
        db.execSQL("DROP TABLE IF EXISTS `rate_providers`");
        db.execSQL("DROP TABLE IF EXISTS `currency_rates`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPlayground = new HashMap<String, TableInfo.Column>(6);
        _columnsPlayground.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayground.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayground.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayground.put("lat", new TableInfo.Column("lat", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayground.put("lon", new TableInfo.Column("lon", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayground.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlayground = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlayground = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlayground = new TableInfo("playground", _columnsPlayground, _foreignKeysPlayground, _indicesPlayground);
        final TableInfo _existingPlayground = TableInfo.read(db, "playground");
        if (!_infoPlayground.equals(_existingPlayground)) {
          return new RoomOpenHelper.ValidationResult(false, "playground(com.currency.rateman.data.db.PlaygroundEntity).\n"
                  + " Expected:\n" + _infoPlayground + "\n"
                  + " Found:\n" + _existingPlayground);
        }
        final HashMap<String, TableInfo.Column> _columnsRateProviders = new HashMap<String, TableInfo.Column>(5);
        _columnsRateProviders.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRateProviders.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRateProviders.put("baseCurrency", new TableInfo.Column("baseCurrency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRateProviders.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRateProviders.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRateProviders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRateProviders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRateProviders = new TableInfo("rate_providers", _columnsRateProviders, _foreignKeysRateProviders, _indicesRateProviders);
        final TableInfo _existingRateProviders = TableInfo.read(db, "rate_providers");
        if (!_infoRateProviders.equals(_existingRateProviders)) {
          return new RoomOpenHelper.ValidationResult(false, "rate_providers(com.currency.rateman.data.db.entity.RateProviderEntity).\n"
                  + " Expected:\n" + _infoRateProviders + "\n"
                  + " Found:\n" + _existingRateProviders);
        }
        final HashMap<String, TableInfo.Column> _columnsCurrencyRates = new HashMap<String, TableInfo.Column>(6);
        _columnsCurrencyRates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurrencyRates.put("providerId", new TableInfo.Column("providerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurrencyRates.put("foreignCurrency", new TableInfo.Column("foreignCurrency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurrencyRates.put("buyRate", new TableInfo.Column("buyRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurrencyRates.put("sellRate", new TableInfo.Column("sellRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurrencyRates.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCurrencyRates = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCurrencyRates.add(new TableInfo.ForeignKey("rate_providers", "CASCADE", "CASCADE", Arrays.asList("providerId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCurrencyRates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCurrencyRates = new TableInfo("currency_rates", _columnsCurrencyRates, _foreignKeysCurrencyRates, _indicesCurrencyRates);
        final TableInfo _existingCurrencyRates = TableInfo.read(db, "currency_rates");
        if (!_infoCurrencyRates.equals(_existingCurrencyRates)) {
          return new RoomOpenHelper.ValidationResult(false, "currency_rates(com.currency.rateman.data.db.entity.CurrencyRateEntity).\n"
                  + " Expected:\n" + _infoCurrencyRates + "\n"
                  + " Found:\n" + _existingCurrencyRates);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d364092a6f6098aa97b396b66279ea9a", "2c44ba98980be13f8968873077582cb6");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "playground","rate_providers","currency_rates");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `playground`");
      _db.execSQL("DELETE FROM `rate_providers`");
      _db.execSQL("DELETE FROM `currency_rates`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PlaygroundDao.class, PlaygroundDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RateProviderDao.class, RateProviderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CurrencyRateDao.class, CurrencyRateDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PlaygroundDao playgroundDao() {
    if (_playgroundDao != null) {
      return _playgroundDao;
    } else {
      synchronized(this) {
        if(_playgroundDao == null) {
          _playgroundDao = new PlaygroundDao_Impl(this);
        }
        return _playgroundDao;
      }
    }
  }

  @Override
  public RateProviderDao rateProviderDao() {
    if (_rateProviderDao != null) {
      return _rateProviderDao;
    } else {
      synchronized(this) {
        if(_rateProviderDao == null) {
          _rateProviderDao = new RateProviderDao_Impl(this);
        }
        return _rateProviderDao;
      }
    }
  }

  @Override
  public CurrencyRateDao currencyRateDao() {
    if (_currencyRateDao != null) {
      return _currencyRateDao;
    } else {
      synchronized(this) {
        if(_currencyRateDao == null) {
          _currencyRateDao = new CurrencyRateDao_Impl(this);
        }
        return _currencyRateDao;
      }
    }
  }
}

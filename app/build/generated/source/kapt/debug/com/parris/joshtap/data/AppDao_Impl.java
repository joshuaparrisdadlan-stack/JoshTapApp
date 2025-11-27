package com.parris.joshtap.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDao_Impl implements AppDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TrackEntity> __insertionAdapterOfTrackEntity;

  private final EntityInsertionAdapter<CardEntity> __insertionAdapterOfCardEntity;

  private final EntityInsertionAdapter<CardTrackJoin> __insertionAdapterOfCardTrackJoin;

  private final SharedSQLiteStatement __preparedStmtOfDeleteJoinsForCard;

  public AppDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTrackEntity = new EntityInsertionAdapter<TrackEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `tracks` (`id`,`display_name`,`local_uri`,`duration_ms`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TrackEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getDisplayName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDisplayName());
        }
        if (value.getLocalUri() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLocalUri());
        }
        stmt.bindLong(4, value.getDurationMs());
      }
    };
    this.__insertionAdapterOfCardEntity = new EntityInsertionAdapter<CardEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `cards` (`id`,`name`,`token`,`created_at`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CardEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getToken() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getToken());
        }
        stmt.bindLong(4, value.getCreatedAt());
      }
    };
    this.__insertionAdapterOfCardTrackJoin = new EntityInsertionAdapter<CardTrackJoin>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `card_track_join` (`card_id`,`track_id`,`position`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CardTrackJoin value) {
        stmt.bindLong(1, value.getCardId());
        stmt.bindLong(2, value.getTrackId());
        stmt.bindLong(3, value.getPosition());
      }
    };
    this.__preparedStmtOfDeleteJoinsForCard = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM card_track_join WHERE card_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertTrack(final TrackEntity track, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfTrackEntity.insertAndReturnId(track);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCard(final CardEntity card, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfCardEntity.insertAndReturnId(card);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertJoin(final CardTrackJoin join, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCardTrackJoin.insert(join);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setCardTracks(final long cardId, final List<Long> orderedTrackIds,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> AppDao.DefaultImpls.setCardTracks(AppDao_Impl.this, cardId, orderedTrackIds, __cont), $completion);
  }

  @Override
  public Object deleteJoinsForCard(final long cardId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteJoinsForCard.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cardId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteJoinsForCard.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object listTracks(final Continuation<? super List<TrackEntity>> $completion) {
    final String _sql = "SELECT * FROM tracks ORDER BY display_name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TrackEntity>>() {
      @Override
      public List<TrackEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "display_name");
          final int _cursorIndexOfLocalUri = CursorUtil.getColumnIndexOrThrow(_cursor, "local_uri");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "duration_ms");
          final List<TrackEntity> _result = new ArrayList<TrackEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TrackEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpLocalUri;
            if (_cursor.isNull(_cursorIndexOfLocalUri)) {
              _tmpLocalUri = null;
            } else {
              _tmpLocalUri = _cursor.getString(_cursorIndexOfLocalUri);
            }
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            _item = new TrackEntity(_tmpId,_tmpDisplayName,_tmpLocalUri,_tmpDurationMs);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object listCards(final Continuation<? super List<CardEntity>> $completion) {
    final String _sql = "SELECT * FROM cards ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CardEntity>>() {
      @Override
      public List<CardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfToken = CursorUtil.getColumnIndexOrThrow(_cursor, "token");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<CardEntity> _result = new ArrayList<CardEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CardEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpToken;
            if (_cursor.isNull(_cursorIndexOfToken)) {
              _tmpToken = null;
            } else {
              _tmpToken = _cursor.getString(_cursorIndexOfToken);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CardEntity(_tmpId,_tmpName,_tmpToken,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCardWithTracksByToken(final String token,
      final Continuation<? super CardWithTracks> $completion) {
    final String _sql = "SELECT * FROM cards WHERE token = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (token == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, token);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, true, _cancellationSignal, new Callable<CardWithTracks>() {
      @Override
      public CardWithTracks call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfToken = CursorUtil.getColumnIndexOrThrow(_cursor, "token");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final LongSparseArray<ArrayList<TrackEntity>> _collectionTracks = new LongSparseArray<ArrayList<TrackEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey = _cursor.getLong(_cursorIndexOfId);
              ArrayList<TrackEntity> _tmpTracksCollection = _collectionTracks.get(_tmpKey);
              if (_tmpTracksCollection == null) {
                _tmpTracksCollection = new ArrayList<TrackEntity>();
                _collectionTracks.put(_tmpKey, _tmpTracksCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiptracksAscomParrisJoshtapDataTrackEntity(_collectionTracks);
            final CardWithTracks _result;
            if(_cursor.moveToFirst()) {
              final CardEntity _tmpCard;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final String _tmpName;
              if (_cursor.isNull(_cursorIndexOfName)) {
                _tmpName = null;
              } else {
                _tmpName = _cursor.getString(_cursorIndexOfName);
              }
              final String _tmpToken;
              if (_cursor.isNull(_cursorIndexOfToken)) {
                _tmpToken = null;
              } else {
                _tmpToken = _cursor.getString(_cursorIndexOfToken);
              }
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              _tmpCard = new CardEntity(_tmpId,_tmpName,_tmpToken,_tmpCreatedAt);
              ArrayList<TrackEntity> _tmpTracksCollection_1 = null;
              final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpTracksCollection_1 = _collectionTracks.get(_tmpKey_1);
              if (_tmpTracksCollection_1 == null) {
                _tmpTracksCollection_1 = new ArrayList<TrackEntity>();
              }
              _result = new CardWithTracks(_tmpCard,_tmpTracksCollection_1);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
            _statement.release();
          }
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCardWithTracksById(final long cardId,
      final Continuation<? super CardWithTracks> $completion) {
    final String _sql = "SELECT * FROM cards WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cardId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, true, _cancellationSignal, new Callable<CardWithTracks>() {
      @Override
      public CardWithTracks call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfToken = CursorUtil.getColumnIndexOrThrow(_cursor, "token");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final LongSparseArray<ArrayList<TrackEntity>> _collectionTracks = new LongSparseArray<ArrayList<TrackEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey = _cursor.getLong(_cursorIndexOfId);
              ArrayList<TrackEntity> _tmpTracksCollection = _collectionTracks.get(_tmpKey);
              if (_tmpTracksCollection == null) {
                _tmpTracksCollection = new ArrayList<TrackEntity>();
                _collectionTracks.put(_tmpKey, _tmpTracksCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiptracksAscomParrisJoshtapDataTrackEntity(_collectionTracks);
            final CardWithTracks _result;
            if(_cursor.moveToFirst()) {
              final CardEntity _tmpCard;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final String _tmpName;
              if (_cursor.isNull(_cursorIndexOfName)) {
                _tmpName = null;
              } else {
                _tmpName = _cursor.getString(_cursorIndexOfName);
              }
              final String _tmpToken;
              if (_cursor.isNull(_cursorIndexOfToken)) {
                _tmpToken = null;
              } else {
                _tmpToken = _cursor.getString(_cursorIndexOfToken);
              }
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              _tmpCard = new CardEntity(_tmpId,_tmpName,_tmpToken,_tmpCreatedAt);
              ArrayList<TrackEntity> _tmpTracksCollection_1 = null;
              final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpTracksCollection_1 = _collectionTracks.get(_tmpKey_1);
              if (_tmpTracksCollection_1 == null) {
                _tmpTracksCollection_1 = new ArrayList<TrackEntity>();
              }
              _result = new CardWithTracks(_tmpCard,_tmpTracksCollection_1);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
            _statement.release();
          }
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshiptracksAscomParrisJoshtapDataTrackEntity(
      final LongSparseArray<ArrayList<TrackEntity>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<TrackEntity>> _tmpInnerMap = new LongSparseArray<ArrayList<TrackEntity>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptracksAscomParrisJoshtapDataTrackEntity(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<TrackEntity>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptracksAscomParrisJoshtapDataTrackEntity(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `tracks`.`id` AS `id`,`tracks`.`display_name` AS `display_name`,`tracks`.`local_uri` AS `local_uri`,`tracks`.`duration_ms` AS `duration_ms`,_junction.`card_id` FROM `card_track_join` AS _junction INNER JOIN `tracks` ON (_junction.`track_id` = `tracks`.`id`) WHERE _junction.`card_id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = 4; // _junction.card_id;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfDisplayName = 1;
      final int _cursorIndexOfLocalUri = 2;
      final int _cursorIndexOfDurationMs = 3;
      while(_cursor.moveToNext()) {
        final long _tmpKey = _cursor.getLong(_itemKeyIndex);
        ArrayList<TrackEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final TrackEntity _item_1;
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          final String _tmpDisplayName;
          if (_cursor.isNull(_cursorIndexOfDisplayName)) {
            _tmpDisplayName = null;
          } else {
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
          }
          final String _tmpLocalUri;
          if (_cursor.isNull(_cursorIndexOfLocalUri)) {
            _tmpLocalUri = null;
          } else {
            _tmpLocalUri = _cursor.getString(_cursorIndexOfLocalUri);
          }
          final long _tmpDurationMs;
          _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
          _item_1 = new TrackEntity(_tmpId,_tmpDisplayName,_tmpLocalUri,_tmpDurationMs);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}

package com.h.myapplication.login.repo.db;


import com.hiking.common.bean.login.ReceivedEvent;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
interface UserReceivedEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ReceivedEvent> receivedEvents);

    @Query("SELECT * FROM user_received_events ORDER BY indexInResponse ASC")
    DataSource.Factory<Integer, ReceivedEvent> queryEvents();

    @Query("DELETE FROM user_received_events")
    void clearReceivedEvents();

    @Query("SELECT MAX(indexInResponse) + 1 FROM user_received_events")
    int getNextIndexInReceivedEvents();
}

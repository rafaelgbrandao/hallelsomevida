package com.hallel.data.menu

import androidx.room.*
import com.hallel.data.event.Event

@Dao
interface MenuDao {

    /* Refer to menu */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMenuItems(menu: Menu)

    /*
    * Refer to event menu
    */
    @Query(SELECT_MENU_FROM_EVENT)
    fun getMenuFromEvent(): List<Menu>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMenuFromEvent(menuList: List<EventMenu>)

    companion object {
        const val SELECT_MENU_FROM_EVENT = "Select m.* from ${Menu.TABLE_NAME} as m " +
                "INNER JOIN ${EventMenu.TABLE_NAME} as em " +
                "ON m.${Menu.COLUMN_ID} = em.${EventMenu.COLUMN_MENU_ID} " +
                "INNER JOIN ${Event.TABLE_NAME} as e " +
                "ON em.${EventMenu.COLUMN_EVENT_ID} = e.${Event.COLUMN_ID} " +
                "where e.${Event.COLUMN_ACTIVE} = 1 " +
                "ORDER BY em.${EventMenu.COLUMN_POSITION} ASC"
    }
}
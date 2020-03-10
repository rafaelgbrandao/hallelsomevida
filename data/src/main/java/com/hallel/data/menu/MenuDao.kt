package com.hallel.data.menu

import androidx.room.*

@Dao
interface MenuDao {

    /* Refer to menu */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMenuItems(menu: Menu)

    /*
    * Refer to event menu
    */
    @Query("$SELECT_MENU_FROM_EVENT :eventId")
    fun getMenuFromEvent(eventId: Int): List<Menu>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMenuFromEvent(menuList: List<EventMenu>)

    companion object {
        const val SELECT_MENU_FROM_EVENT = "Select m.* from ${Menu.TABLE_NAME} as m " +
                "INNER JOIN ${EventMenu.TABLE_NAME} as em " +
                "ON m.${Menu.COLUMN_ID} = em.${EventMenu.COLUMN_MENU_ID} " +
                "where em.${EventMenu.COLUMN_EVENT_ID} = "
    }
}
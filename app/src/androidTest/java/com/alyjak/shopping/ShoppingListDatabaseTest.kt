package com.alyjak.shopping

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.runner.AndroidJUnit4
import com.alyjak.shopping.database.ProductDao
import com.alyjak.shopping.database.ShoppingListDao
import com.alyjak.shopping.database.ShoppingListDatabase
import com.alyjak.shopping.database.model.Product
import com.alyjak.shopping.database.model.ShoppingList
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ShoppingListDatabaseTest {

    private lateinit var shoppingListDao: ShoppingListDao
    private lateinit var productDao: ProductDao
    private lateinit var db: ShoppingListDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(
            context, ShoppingListDatabase::class.java).build()
        shoppingListDao = db.shoppingListDao
        productDao = db.productDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeShoppingListAndReadIt() {
        val shoppingList = ShoppingList(listId = 1L, listName = "List1")
        shoppingListDao.insert(shoppingList)
        val shoppingListInDb = shoppingListDao.get(shoppingList.listId)
        assertThat(shoppingList.listName, equalTo(shoppingListInDb.listName))
    }

    @Test
    @Throws(Exception::class)
    fun writeProductAndReadIt() {
        val product1 = Product(productId = 1L, shoppingListId = 1L, name = "product1")
        productDao.insert(product1)
        val shoppingListInDb = productDao.get(product1.productId)
        assertThat(product1.name, equalTo(shoppingListInDb.name))
    }

    @Test
    @Throws(Exception::class)
    fun writeShoppingListWithProductAndReadIt() {
        val shoppingList = ShoppingList(listId = 1L, listName = "List1")
        shoppingListDao.insert(shoppingList)
        val product = Product(productId = 2L, shoppingListId = 1L, name = "product1")
        productDao.insert(product)
        val shoppingListInDb = shoppingListDao.getShoppingListWithProducts(shoppingList.listId)
        assertThat(shoppingList.listName, equalTo(shoppingListInDb.shoppingList.listName))
        assertThat(product.name, equalTo(shoppingListInDb.products[0].name))
    }

    @Test
    @Throws(Exception::class)
    fun writeShoppingListWithProductsAndReadIt() {
        val shoppingList = ShoppingList(listId = 1L, listName = "List1")
        shoppingListDao.insert(shoppingList)
        val product1 = Product(productId = 1L, shoppingListId = 1L, name = "product1")
        productDao.insert(product1)
        val product2 = Product(productId = 2L, shoppingListId = 1L, name = "product2")
        productDao.insert(product2)
        val product3 = Product(productId = 3L, shoppingListId = 1L, name = "product3")
        productDao.insert(product3)
        val shoppingListInDb = shoppingListDao.getShoppingListWithProducts(shoppingList.listId)
        assertThat(shoppingList.listName, equalTo(shoppingListInDb.shoppingList.listName))
        assertThat(3, equalTo(shoppingListInDb.products.size))
    }

    @Test
    @Throws(Exception::class)
    fun updateShoppingListWithProductsAndReadIt() {
        val shoppingList = ShoppingList(listId = 1L, listName = "List1")
        shoppingListDao.insert(shoppingList)
        val product1 = Product(productId = 1L, shoppingListId = 1L, name = "product1")
        productDao.insert(product1)
        val product2 = Product(productId = 2L, shoppingListId = 1L, name = "product2")
        productDao.insert(product2)
        val product3 = Product(productId = 3L, shoppingListId = 1L, name = "product3")
        productDao.insert(product3)
        shoppingList.archived = true
        shoppingListDao.update(shoppingList)
        val shoppingListInDb = shoppingListDao.getShoppingListWithProducts(shoppingList.listId)
        assertThat(shoppingList.listName, equalTo(shoppingListInDb.shoppingList.listName))
        assertThat(shoppingList.archived, equalTo(shoppingListInDb.shoppingList.archived))
        assertThat(3, equalTo(shoppingListInDb.products.size))
    }

    @Test
    @Throws(Exception::class)
    fun deleteShoppingListWithProductsAndReadIt() {
        val shoppingList = ShoppingList(listId = 1L, listName = "List1")
        shoppingListDao.insert(shoppingList)
        shoppingListDao.delete(shoppingList)
        val shoppingListInDb = shoppingListDao.get(shoppingList.listId)
        Assert.assertEquals(true, shoppingListInDb == null)
    }

    @Test
    @Throws(Exception::class)
    fun getArchivedShoppingLists() {
        val shoppingList1 = ShoppingList(listId = 1L, listName = "List1")
        shoppingListDao.insert(shoppingList1)
        val shoppingList2 = ShoppingList(listId = 2L, listName = "List2", archived = true)
        shoppingListDao.insert(shoppingList2)
        val shoppingList3 = ShoppingList(listId = 3L, listName = "List3", archived = true)
        shoppingListDao.insert(shoppingList3)
        val product1 = Product(productId = 1L, shoppingListId = 1L, name = "product1")
        productDao.insert(product1)
        val product2 = Product(productId = 2L, shoppingListId = 2L, name = "product2")
        productDao.insert(product2)
        val product3 = Product(productId = 3L, shoppingListId = 3L, name = "product3")
        productDao.insert(product3)
        val shoppingListsInDb = shoppingListDao.getAllArchiveShoppingList()
        Assert.assertEquals(2, shoppingListsInDb.size)
    }

    @Test
    @Throws(Exception::class)
    fun getOnlyActiveShoppingLists() {
        val shoppingList1 = ShoppingList(listId = 1L, listName = "List1")
        shoppingListDao.insert(shoppingList1)
        val shoppingList2 = ShoppingList(listId = 2L, listName = "List2", archived = true)
        shoppingListDao.insert(shoppingList2)
        val shoppingList3 = ShoppingList(listId = 3L, listName = "List3", archived = true)
        shoppingListDao.insert(shoppingList3)
        val product1 = Product(productId = 1L, shoppingListId = 1L, name = "product1")
        productDao.insert(product1)
        val product2 = Product(productId = 2L, shoppingListId = 2L, name = "product2")
        productDao.insert(product2)
        val product3 = Product(productId = 3L, shoppingListId = 3L, name = "product3")
        productDao.insert(product3)
        val shoppingListsInDb = shoppingListDao.getAllActiveShoppingList()
        Assert.assertEquals(1, shoppingListsInDb.size)
    }

    @Test
    @Throws(Exception::class)
    fun clearAllShoppingListTable() {
        val shoppingList = ShoppingList(listId = 1L, listName = "List1")
        val shoppingList2 = ShoppingList(listId = 2L, listName = "List2")
        val shoppingList3 = ShoppingList(listId = 3L, listName = "List3")
        shoppingListDao.insert(shoppingList)
        shoppingListDao.insert(shoppingList2)
        shoppingListDao.insert(shoppingList3)
        shoppingListDao.clear()
        val shoppingListInDb = shoppingListDao.getAllActiveShoppingList()
        Assert.assertEquals(true, shoppingListInDb.isEmpty())
    }

}